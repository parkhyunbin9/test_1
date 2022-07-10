package com.reco.prob.domain.history;

import com.reco.prob.domain.customer.Customer;
import com.reco.prob.domain.imagemeta.CollectImageMetaService;
import com.reco.prob.dto.CollectResponse;
import com.reco.prob.dto.CustomerResponseDto;
import com.reco.prob.exception.NoSuchHistoryException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CollectHistoryService {

    private final CollectHistoryRepository collectHistoryRepository;
    private final CollectImageMetaService imageMetaService;

    public CollectHistory findById(Long id) {
        return collectHistoryRepository.findById(id).orElseThrow(() -> new NoSuchHistoryException("이력이 없습니다."));
    }

    public CustomerResponseDto getCustomerResponseDto(Long id) {
        CollectHistory history = findById(id);
        return new CustomerResponseDto(history.getCustomer().getName(), history.getCustomer().getLocale(),
                history.getCollect().getQuantaty(), history.getCollect().getCanCount());
    }

    public List<CustomerResponseDto> getCustomerResponseDto(Customer customer) {
        List<CollectHistory> historyList = findByCustomer(customer);
        return historyList.stream().map(history -> new CustomerResponseDto(history.getCustomer().getName(), history.getCustomer().getLocale(),
                history.getCollect().getQuantaty(), history.getCollect().getCanCount()))
                .collect(Collectors.toList());
    }

    public List<CollectHistory> findByCustomer(Customer customer) {
        return collectHistoryRepository.findByCustomer(customer);
    }

    public List<CollectHistory> findByCustomer(Customer customer, Pageable pageable) {
        return collectHistoryRepository.findByCustomer(customer, pageable);
    }


    public List<CollectHistory> findByCollectTimeRange(LocalDateTime start, LocalDateTime end) throws IllegalArgumentException{
        if(!start.isBefore(end)) {
            throw new IllegalArgumentException("시작시간은 종료시간보다 과거여야 합니다");
        } else {
            return collectHistoryRepository.findByCollectTimeGreaterThanEqualAndCollectTimeLessThan(start, end);
        }
    }
    public List<CollectResponse> getCollectResponse(LocalDateTime start, LocalDateTime end) throws IllegalArgumentException{
        if(!start.isBefore(end)) {
            throw new IllegalArgumentException("시작시간은 종료시간보다 과거여야 합니다");
        } else {
            List<CollectHistory> histories = collectHistoryRepository.findByCollectTimeGreaterThanEqualAndCollectTimeLessThan(start, end);
            return histories.stream().map(e -> new CollectResponse().getCollectResponse(e.getCustomer(), e, imageMetaService.findByCollectHistory(e)))
                    .collect(Collectors.toList());
        }
    }

    public List<CollectHistory> findAll() {
        return collectHistoryRepository.findAll();
    }

    @Transactional
    public CollectHistory save(CollectHistory history) {
        return collectHistoryRepository.save(history);
    }

    @Transactional
    public void remove(CollectHistory history) {
        imageMetaService.removeByHisotryHistory(history);
        collectHistoryRepository.delete(history);
    }
}
