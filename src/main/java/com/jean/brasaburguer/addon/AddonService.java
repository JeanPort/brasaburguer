package com.jean.brasaburguer.addon;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AddonService {
    private final AddonRepository repository;

    public AddonService(AddonRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<AddonResponse> findAll(){
        return repository.findAll().stream()
                .map(AddonResponse::toAddonResponse).toList();
    }

    @Transactional(readOnly = true)
    public AddonResponse findById(Long addonId){
        var addon = findByIdOrThrow(addonId);
        return AddonResponse.toAddonResponse(addon);
    }

    public AddonResponse create(AddonRequest request){
        var addon = Addon.create(request.name(), request.price());
        repository.save(addon);
        return AddonResponse.toAddonResponse(addon);
    }

    public AddonResponse update(Long addonId, AddonRequest request){
        var addon = findByIdOrThrow(addonId);
        addon.update(request.name(), request.price());
        return AddonResponse.toAddonResponse(addon);
    }

    public void activate(Long addonId){
        var addon = findByIdOrThrow(addonId);
        addon.acivate();
    }

    public void deactivate(Long addonId){
        var addon = findByIdOrThrow(addonId);
        addon.deactivate();
    }

    private Addon findByIdOrThrow(Long addonId) {
        return repository.findById(addonId)
                .orElseThrow(() -> new AddonNotFoundException(addonId));
    }

}
