package com.jean.brasaburguer.addon;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addons")
public class AddonController {
    private final AddonService service;

    public AddonController(AddonService service) {
        this.service = service;
    }

    @GetMapping
    public List<AddonResponse> findAll(){
        return service.findAll();
    }

    @GetMapping("/{addonId}")
    public AddonResponse findById(@PathVariable Long addonId){
        return service.findById(addonId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AddonResponse create(@RequestBody @Valid AddonRequest request){
        return service.create(request);
    }

    @PutMapping("/{addonId}")
    public AddonResponse update(@PathVariable Long addonId, @RequestBody @Valid AddonRequest request){
        return service.update(addonId, request);
    }

    @PatchMapping("/{addonId}/activate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activate(@PathVariable Long addonId){
        service.activate(addonId);
    }

    @PatchMapping("/{addonId}/deactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivate(@PathVariable Long addonId){
        service.deactivate(addonId);
    }
}
