package com.reco.prob.domain.imagemeta;

import com.reco.prob.domain.history.CollectHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CollectImageMetaService {

    private final CollectImageMetaRepository imageMetaRepository;

    @Transactional
    public CollectImageMeta save(CollectImageMeta imageMeta) {
        imageMeta.setName(validName(imageMeta.getName(), imageMeta.getCollectHistory()));
        imageMeta.updateImageCount(1);
        return imageMetaRepository.save(imageMeta);
    }

    @Transactional
    public CollectImageMeta updateById(Long id, CollectImageMeta updateImageMeta) {
        CollectImageMeta beforeImageMeta = imageMetaRepository.findById(id).get();
        beforeImageMeta.setName(updateImageMeta.getName());
        return beforeImageMeta;
    }

    public String getDefaultNameByCustomer(CollectHistory history) {
        StringBuilder sb = new StringBuilder();
        String customerName = history.getCustomer().getName().toLowerCase();
        int sameStoreNameImageCnt  = imageMetaRepository.findByNameStartsWith(customerName).size();
        String ImageNameNum = (sameStoreNameImageCnt < 10) ? "0".concat(String.valueOf(sameStoreNameImageCnt + 1)) : String.valueOf(sameStoreNameImageCnt + 1);
        return sb.append(customerName).append("_").append(ImageNameNum).toString();
    }

    public String validName(String name, CollectHistory history) {
        return (ObjectUtils.isEmpty(name) || isExistName(name)) ? getDefaultNameByCustomer(history) : name;
    }

    public List<CollectImageMeta> findAll() {
        return imageMetaRepository.findAll();
    }

    public List<CollectImageMeta> findByCollectHistory(CollectHistory history) {
        return imageMetaRepository.findByCollectHistory(history);
    }

    public boolean isExistName(String name) {
        return imageMetaRepository.existsByName(name);
    }

    @Transactional
    public void removeByHisotryHistory(CollectHistory history) {
        findByCollectHistory(history).forEach(this::remove);
    }

    @Transactional
    public void remove(CollectImageMeta imageMeta) {
        imageMetaRepository.delete(imageMeta);
        imageMeta.updateImageCount(-1);
    }
}
