package com.reco.prob.domain.imagemeta;

import com.reco.prob.domain.history.CollectHistory;
import com.reco.prob.utils.TimeUtil;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CollectImageMeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "collect_image_meta_id")
    private Long id;

    @NonNull
    private String name;

    @Enumerated(EnumType.STRING)
    private ImageFormat imageFormat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collect_history_id")
    private CollectHistory collectHistory;

    @CreatedDate
    private LocalDateTime createTime;

    @LastModifiedDate
    private LocalDateTime modifiedTime;

    @PrePersist
    public void onPrePersist(){
        this.createTime = TimeUtil.getLocalDateTimeWithSimpleFormat(this.createTime);
        this.modifiedTime =  TimeUtil.getLocalDateTimeWithSimpleFormat(this.modifiedTime);
    }

    @PreUpdate
    public void onPreUpdate() {
        this.modifiedTime = TimeUtil.getLocalDateTimeWithSimpleFormat(this.modifiedTime);
    }

    public static CollectImageMeta createImageMeta(String name, ImageFormat format, CollectHistory history ) {

        return CollectImageMeta.builder()
                .name(name)
                .imageFormat(format)
                .collectHistory(history)
                .createTime(LocalDateTime.now())
                .modifiedTime(LocalDateTime.now())
                .build();
    }

    public void setName(String name) {
        this.name = name;
    }

//    public void setCollectHistory(CollectHistory history) {
//        this.collectHistory = history;
//    }

    public void updateImageCount(int imageCount) {
        this.collectHistory.updateImageCount(imageCount);
    }

}
