package com.reco.prob.service;

import com.reco.prob.domain.*;
import com.reco.prob.domain.customer.Customer;
import com.reco.prob.domain.customer.CustomerService;
import com.reco.prob.domain.history.CollectHistory;
import com.reco.prob.domain.history.CollectHistoryService;
import com.reco.prob.domain.imagemeta.CollectImageMeta;
import com.reco.prob.domain.imagemeta.CollectImageMetaService;
import com.reco.prob.domain.imagemeta.ImageFormat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CollectImageMetaServiceTest {

    @Autowired
    CollectImageMetaService imageMetaService;
    @Autowired
    CollectHistoryService historyService;
    @Autowired
    CustomerService customerService;

    @DisplayName("같은 이름의 사진이 저장되면 이름뒤에 숫자가 하나 오른다.")
    @Test
    public void 파일명_저장_테스트() {
        //given
        String name = "맘스터치";
        Customer customer1 = Customer.createCustomer(name, "강남", LocalDate.of(2021, 10, 01), "김맘스");

        CollectHistory history =
                CollectHistory.createCollectHistory(customer1,
                        new Collect(4, 3), 3, "새벽에 수거 해야함",
                        LocalDateTime.of(2021, 11, 03, 01, 15, 13));

        customerService.save(customer1);
        historyService.save(history);

        //when
        CollectImageMeta savedImageMeta = imageMetaService.save(CollectImageMeta.createImageMeta(name, ImageFormat.JPG, history));
        CollectImageMeta sameNameImageMeta = imageMetaService.save(CollectImageMeta.createImageMeta("", ImageFormat.JPG, history));

        System.out.println("savedImageMeta.getName() = " + savedImageMeta.getName());
        System.out.println("sameNameImageMeta.getName() = " + sameNameImageMeta.getName());

        //then
        assertNotEquals(savedImageMeta.getName(), sameNameImageMeta.getName());
        assertTrue(sameNameImageMeta.getName().contains("02"));
     }

    @DisplayName("업장의 이미지가 처음 등록되면 파일명은 업장명_01이다..")
    @Test
    public void 새로운_파일명_저장_테스트() {
        String name = "맘스터치";
        Customer customer1 = Customer.createCustomer(name, "강남", LocalDate.of(2021, 10, 01), "김맘스");

        CollectHistory history =
                CollectHistory.createCollectHistory(customer1,
                        new Collect(4, 3), 3, "새벽에 수거 해야함",
                        LocalDateTime.of(2021, 11, 03, 01, 15, 13));

        customerService.save(customer1);
        historyService.save(history);

        //when
        CollectImageMeta savedImageMeta = imageMetaService.save(CollectImageMeta.createImageMeta("", ImageFormat.JPG, history));
        System.out.println("savedImageMeta.getName() = " + savedImageMeta.getName());
        assertTrue(savedImageMeta.getName().contains("01"));
        assertEquals(savedImageMeta.getName(), name.concat("_01"));
    }

    @DisplayName("특정 수거이력에 대한 사진이 추가되면 수거이력 첨부 사진 갯수가 올라간다.")
    @Test
    public void 이미지_추가시_해당_수거이력_첨부사진갯수_증가_테스트() {
        String name = "맘스터치";
        Customer customer1 = Customer.createCustomer(name, "강남", LocalDate.of(2021, 10, 01), "김맘스");

        CollectHistory history =
                CollectHistory.createCollectHistory(customer1,
                        new Collect(4, 3), 3, "새벽에 수거 해야함",
                        LocalDateTime.of(2021, 11, 03, 01, 15, 13));

        customerService.save(customer1);
        CollectHistory savedHistory = historyService.save(history);

        //when
        int imageCountBeforeAdd = savedHistory.getImageCount();
        CollectImageMeta savedImageMeta = imageMetaService.save(CollectImageMeta.createImageMeta("", ImageFormat.JPG, history));
        int imageCountAfterImageAdd = savedImageMeta.getCollectHistory().getImageCount();
        System.out.println("imageCountBeforeAdd = " + imageCountBeforeAdd);
        System.out.println("imageCountAfterImageAdd = " + imageCountAfterImageAdd);

        assertEquals(imageCountAfterImageAdd - imageCountBeforeAdd, 1);
    }

    @DisplayName("특정 수거이력에 대한 사진이 삭제되면 수거이력 첨부 사진 갯수가 내려간다.")
    @Test
    public void 이미지_삭제시_해당_수거이력_첨부사진갯수_감소_테스트() {
        String name = "맘스터치";
        Customer customer1 = Customer.createCustomer(name, "강남", LocalDate.of(2021, 10, 01), "김맘스");

        CollectHistory history =
                CollectHistory.createCollectHistory(customer1,
                        new Collect(4, 3), 3, "새벽에 수거 해야함",
                        LocalDateTime.of(2021, 11, 03, 01, 15, 13));

        customerService.save(customer1);
        CollectHistory savedHistory = historyService.save(history);

        //when
        CollectImageMeta savedImageMeta = imageMetaService.save(CollectImageMeta.createImageMeta("", ImageFormat.JPG, history));

        int imageCountBeforeDelete = savedImageMeta.getCollectHistory().getImageCount();
        imageMetaService.remove(savedImageMeta);
        int imageCountAfterImageDelete = savedImageMeta.getCollectHistory().getImageCount();

        System.out.println("imageCountBeforeDelete = " + imageCountBeforeDelete);
        System.out.println("imageCountAfterImageDelete = " + imageCountAfterImageDelete);

        assertEquals(imageCountBeforeDelete- imageCountAfterImageDelete, 1);
    }

    @DisplayName("수정이 되면 수정시간이 바뀐다.")
    @Test
    public void 수정시간_업데이트_테스트() {
//given
        String name = "맘스터치";
        Customer customer1 = Customer.createCustomer(name, "강남", LocalDate.of(2021, 10, 01), "김맘스");

        CollectHistory history =
                CollectHistory.createCollectHistory(customer1,
                        new Collect(4, 3), 3, "새벽에 수거 해야함",
                        LocalDateTime.of(2021, 11, 03, 01, 15, 13));

        customerService.save(customer1);
        CollectHistory savedHistory = historyService.save(history);

        //when
        CollectImageMeta savedImageMeta = imageMetaService.save(CollectImageMeta.createImageMeta(name, ImageFormat.JPG, history));
        String beforeName = savedImageMeta.getName();
        LocalDateTime beforeModifiedTime = savedImageMeta.getModifiedTime();
        CollectImageMeta updateImageMeta = imageMetaService.updateById(savedImageMeta.getId(), CollectImageMeta.createImageMeta("도미노피자", ImageFormat.JPG, history));

        System.out.println("beforeName = " + beforeName);
        System.out.println("updateImageMeta.getName = " + updateImageMeta.getName());

        System.out.println("beforeModifiedTime = " + beforeModifiedTime);
        System.out.println("updateImageMeta.getModifiedTime = " + updateImageMeta.getModifiedTime());
        //then
        assertNotEquals(beforeName, updateImageMeta.getName());
        assertNotEquals(LocalDateTime.now(), updateImageMeta.getModifiedTime());
    }

    @DisplayName("저장이 성공적으로 잘된다.")
    @Test
    @Rollback(value = false)
    public void save_test() throws Exception {
        List<Customer> customerList = new ArrayList<>();
        customerList.add(Customer.createCustomer("맘스터치", "강남", LocalDate.of(2021, 10, 01), "김맘스"));
        customerList.add(Customer.createCustomer("롯데리아", "서초", LocalDate.of(2021, 11, 01), "박롯데"));
        customerList.add(Customer.createCustomer("베스킨라빈스", "역삼", LocalDate.of(2021, 11, 05), "베스킨"));
        customerList.add(Customer.createCustomer("본죽", "삼성", LocalDate.of(2021, 11, 03), "최본죽"));
        customerList.forEach(customerService::save);


        //when
        List<CollectHistory> historyList = new ArrayList<>();
        historyList.add(CollectHistory.createCollectHistory(customerList.get(0), new Collect(4, 450), 3, "새벽에 수거 해야함", LocalDateTime.of(2021, 11, 03, 01, 15, 13)));
        historyList.add(CollectHistory.createCollectHistory(customerList.get(1), new Collect(1, 110), 1, null, LocalDateTime.of(2021, 11, 03, 13, 25, 36)));
        historyList.add(CollectHistory.createCollectHistory(customerList.get(2), new Collect(3, 362), 2, "하루에 2번 수거", LocalDateTime.of(2021, 11, 03, 12, 25, 14)));
        historyList.add(CollectHistory.createCollectHistory(customerList.get(3), new Collect(6, 651), 1, "매일 수거", LocalDateTime.of(2021, 11, 04, 01, 10, 13)));
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

        assertEquals(imageMetaService.findAll().size() , imageDataList.size());

    }

}