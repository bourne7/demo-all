package com.pbr.plugin.controller;

import com.pbr.plugin.business.MainService;
import com.pbr.plugin.dto.PluginDTO;
import com.pbr.plugin.service.PluginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("plugin")
@Slf4j
public class PluginController {

    AtomicInteger counter = new AtomicInteger(0);

    @Autowired
    MainService mainService;

    @Autowired
    PluginService pluginService;

    @RequestMapping(value = "test/doA", method = RequestMethod.GET)
    public Object doA() {
        return mainService.doA(counter.getAndIncrement());
    }

    @RequestMapping(value = "loadPlugin", method = RequestMethod.POST)
    public Object loadPlugin(@RequestBody PluginDTO pluginDTO) {
        pluginService.loadPlugin(pluginDTO);
        return counter.getAndIncrement();
    }

    @RequestMapping(value = "unloadPlugin", method = RequestMethod.DELETE)
    public Object unloadPlugin(@RequestBody PluginDTO pluginDTO) {
        pluginService.unloadPlugin(pluginDTO);
        return counter.getAndIncrement();
    }

    @RequestMapping(value = "getAllPlugin", method = RequestMethod.GET)
    public Object getAllPlugin() {
        return PluginService.PLUGIN_MAP;
    }

}
