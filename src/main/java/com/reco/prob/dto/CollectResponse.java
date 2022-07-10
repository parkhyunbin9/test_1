package com.reco.prob.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.reco.prob.domain.customer.Customer;
import com.reco.prob.domain.history.CollectHistory;
import com.reco.prob.domain.imagemeta.CollectImageMeta;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CollectResponse {

    private String customerName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime collectRealTime;
    private List<ImageInfo> imageInfo;


    @Getter
    @Setter
    @Builder
    @ToString
    static class ImageInfo {
        private String locale;
        private String ownerName;
        private String imageName;
        private int quantity;
        private int canCount;
    }

    public CollectResponse getCollectResponse(Customer customer, CollectHistory history, List<CollectImageMeta> mageMetaList) {
        List<ImageInfo> imageInfoList = mageMetaList.stream().map(e -> {
            return ImageInfo.builder().imageName(e.getName())
                    .ownerName(customer.getOwnerName())
                    .locale(customer.getLocale())
                    .quantity(history.getCollect().getQuantaty())
                    .canCount(history.getCollect().getCanCount())
                    .build();
        }).collect(Collectors.toList());

        return CollectResponse.builder()
                .customerName(customer.getName())
                .collectRealTime(history.getCollectTime())
                .imageInfo(imageInfoList)
                .build();
    }
}
