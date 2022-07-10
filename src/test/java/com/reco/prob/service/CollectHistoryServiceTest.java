package com.reco.prob.service;

import com.reco.prob.domain.*;
import com.reco.prob.domain.customer.Customer;
import com.reco.prob.domain.customer.CustomerService;
import com.reco.prob.domain.history.CollectHistory;
import com.reco.prob.domain.history.CollectHistoryService;
import com.reco.prob.domain.imagemeta.CollectImageMeta;
import com.reco.prob.domain.imagemeta.CollectImageMetaService;
import com.reco.prob.domain.imagemeta.ImageFormat;
import com.reco.prob.dto.CollectDateRequestDto;
import com.reco.prob.dto.CollectResponse;
import com.reco.prob.exception.NoSuchHistoryException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CollectHistoryServiceTest {

    @Autowired
    CollectHistoryService historyService;
    @Autowired
    CustomerService customerService;
    @Autowired
    CollectImageMetaService imageMetaService;

    @DisplayName("저장이 잘되는지 확인")
    @Test
    public void saveTest() throws Exception {
        //given
        Customer customer1 = customerService.findById(1L);
        Customer customer2 = customerService.findById(2L);
        Customer customer3 = customerService.findById(3L);
        Customer customer4 = customerService.findById(4L);

        //when
        List<CollectHistory> historyList = new ArrayList<>();
        historyList.add(CollectHistory.createCollectHistory(customer1, new Collect(4, 3), 3, "새벽에 수거 해야함", LocalDateTime.of(2021, 11, 03, 01, 15, 13)));
        historyList.add(CollectHistory.createCollectHistory(customer2, new Collect(1, 1), 1, null, LocalDateTime.of(2021, 11, 03, 13, 25, 36)));
        historyList.add(CollectHistory.createCollectHistory(customer3, new Collect(3, 2), 2, "하루에 2번 수거", LocalDateTime.of(2021, 11, 03, 12, 25, 14)));
        historyList.add(CollectHistory.createCollectHistory(customer4, new Collect(6, 1), 1, "매일 수거", LocalDateTime.of(2021, 11, 04, 01, 10, 13)));

        //then
        assertEquals(historyService.findAll().size(), historyList.size());

    }

    @DisplayName("수거 이력이 삭제되면 해당 이력 관련 이미지파일도 삭제되어야합니다.")
    @Test
    public void 이력_삭제_테스트() throws Exception {
        //given
        Customer customer = Customer.createCustomer("맘스터치", "강남", LocalDate.of(2021, 10, 01), "김맘스");
        customerService.save(customer);
        CollectHistory history = historyService.save(CollectHistory.createCollectHistory(customer, new Collect(4, 450), 3, "새벽에 수거 해야함", LocalDateTime.of(2021, 11, 03, 01, 15, 13)));

        List<CollectImageMeta> imageDataList = new ArrayList<>();

        imageDataList.add(CollectImageMeta.createImageMeta("", ImageFormat.JPG, history));
        imageDataList.add(CollectImageMeta.createImageMeta("", ImageFormat.JPG, history));
        imageDataList.add(CollectImageMeta.createImageMeta("", ImageFormat.JPG, history));
        imageDataList.forEach(imageMetaService::save);

        //when
        historyService.remove(history);
        List<CollectImageMeta> deletedImageMetaList = imageMetaService.findByCollectHistory(history);

        //then
        assertEquals(deletedImageMetaList.size(), 0);
        assertThrows(NoSuchHistoryException.class, () -> historyService.findById(history.getId()));
    }

    @DisplayName("특이사항이 저장되지 않으면 기본값 '없읍' 저장")
    @Test
    public void note_save_default_test() throws Exception {
        //given
        Customer customer = Customer.createCustomer("맘스터치", "강남", LocalDate.of(2021, 10, 01), "김맘스");
        customerService.save(customer);
        CollectHistory collectHistory = CollectHistory.createCollectHistory(customer, new Collect(1, 1), 3, "", LocalDateTime.now());

        //when
        CollectHistory history = historyService.save(collectHistory);

        //then
        assertEquals(history.getNotes(), "없음");
    }

    @DisplayName("시간범위로 이력리스트 가져오기")
    @Test
    public void collect_time_range_test() throws Exception {
        //given
        Customer customer = Customer.createCustomer("맘스터치", "강남", LocalDate.of(2021, 10, 01), "김맘스");
        customerService.save(customer);
        List<CollectHistory> historyList = new ArrayList<>();
        historyList.add(CollectHistory.createCollectHistory(customer, new Collect(4, 3), 3, "새벽에 수거 해야함", LocalDateTime.of(2022, 07, 01, 01, 15, 13)));
        historyList.add(CollectHistory.createCollectHistory(customer, new Collect(1, 1), 1, null, LocalDateTime.of(2022, 07, 01, 23, 59, 59)));
        historyList.add(CollectHistory.createCollectHistory(customer, new Collect(3, 2), 2, "하루에 2번 수거", LocalDateTime.of(2022, 06, 30, 0, 0, 0)));
        historyList.add(CollectHistory.createCollectHistory(customer, new Collect(6, 1), 1, "매일 수거", LocalDateTime.of(2022, 07, 10, 01, 10, 13)));
        historyList.forEach(historyService::save);
        //when
        String inputDateStr = "2022-07-01";
        CollectDateRequestDto inputDate = new CollectDateRequestDto(inputDateStr);
        //then
        List<CollectHistory> byCollectTimeRange = historyService.findByCollectTimeRange(inputDate.getCollectStart(), inputDate.getCollectEnd());

        assertEquals(byCollectTimeRange.size(), 2);
    }

    @DisplayName("날짜 범위로 수거 관련 응답 객체 반환")
    @Test
    public void getCollectResponseTest() throws Exception {
        //given

        String inputDateStr = "2022-07-01";
        CollectDateRequestDto inputDate = new CollectDateRequestDto(inputDateStr);
        //given
        Customer customer = Customer.createCustomer("맘스터치", "강남", LocalDate.of(2021, 10, 01), "김맘스");
        customerService.save(customer);
        List<CollectHistory> historyList = new ArrayList<>();
        historyList.add(CollectHistory.createCollectHistory(customer, new Collect(4, 3), 3, "새벽에 수거 해야함", LocalDateTime.of(2022, 07, 01, 01, 15, 13)));
        historyList.add(CollectHistory.createCollectHistory(customer, new Collect(1, 1), 1, null, LocalDateTime.of(2022, 07, 01, 23, 59, 59)));
        historyList.add(CollectHistory.createCollectHistory(customer, new Collect(3, 2), 2, "하루에 2번 수거", LocalDateTime.of(2022, 06, 30, 0, 0, 0)));
        historyList.add(CollectHistory.createCollectHistory(customer, new Collect(6, 1), 1, "매일 수거", LocalDateTime.of(2022, 07, 10, 01, 10, 13)));
        historyList.forEach(historyService::save);

        List<CollectImageMeta> imageDataList = new ArrayList<>();

        imageDataList.add(CollectImageMeta.createImageMeta("", ImageFormat.JPG, historyList.get(0)));
        imageDataList.add(CollectImageMeta.createImageMeta("", ImageFormat.JPG, historyList.get(0)));
        imageDataList.add(CollectImageMeta.createImageMeta("", ImageFormat.JPG, historyList.get(0)));

        imageDataList.add(CollectImageMeta.createImageMeta("", ImageFormat.PNG, historyList.get(1)));

        imageDataList.add(CollectImageMeta.createImageMeta("", ImageFormat.JPEG, historyList.get(2)));
        imageDataList.add(CollectImageMeta.createImageMeta("", ImageFormat.JPEG, historyList.get(2)));
        imageDataList.add(CollectImageMeta.createImageMeta("", ImageFormat.JPG, historyList.get(3)));

        imageDataList.forEach(imageMetaService::save);
        //when
        List<CollectResponse> collectResponse = historyService.getCollectResponse(inputDate.getCollectStart(), inputDate.getCollectEnd());
        //then
        System.out.println("=======================");
        collectResponse.forEach(System.out::println);
        System.out.println("***********************8");
    }
}