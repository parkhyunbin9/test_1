package com.reco.prob.domain.history;

import com.reco.prob.domain.Collect;
import com.reco.prob.domain.customer.Customer;
import com.reco.prob.utils.TimeUtil;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CollectHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "collect_history_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Embedded
    Collect collect;

    @ColumnDefault(value = "0")
    private int imageCount;

    @Lob
    private String notes;

    private LocalDateTime collectTime;
//
//    @OneToMany(mappedBy = "collectImageMeta", cascade = CascadeType.ALL)
//    private List<CollectImageMeta> imageList = new ArrayList<>();

    @PrePersist
    @PreUpdate
    public void onPrePersist(){
        this.collectTime = TimeUtil.getLocalDateTimeWithSimpleFormat(this.collectTime);
        if(ObjectUtils.isEmpty(this.notes)) this.notes = "없음";
    }

    //**생성 메서드**//
    public static CollectHistory createCollectHistory(Customer customer, Collect collect
            , int imageCount, String notes, LocalDateTime collectTime) {

        return CollectHistory.builder()
                .customer(customer)
                .collect(collect)
                .imageCount(imageCount)
                .notes(notes)
                .collectTime(collectTime)
                .build();
    }
//    //**연관관계 메서드**//
//    public void addImageMeta(CollectImageMeta imageMeta) {
//        imageList.add(imageMeta);
//        imageMeta.setCollectHistory(this);
//    }
    //**비즈니스 로직**//
    public void updateImageCount(int imageCount) {
        this.imageCount += imageCount;
    }

}

