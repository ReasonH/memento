package com.memento.web.controller;

import com.memento.web.dto.SortType;
import com.memento.web.service.ExtensionService;
import com.memento.web.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class SearchController {
    private final ExtensionService extensionService;
    private final SearchService searchService;

    @GetMapping("/log")
    public String log(@PageableDefault(size=10)Pageable pageable,
                      @RequestParam("name") String name, Model model) {
        extensionService.saveToMainDB(name);
        model.addAttribute("historyList", searchService.findAllByNameWithPageination(name, pageable));
        model.addAttribute("userName", name);
        return "log";
    }

    @GetMapping("/search")
    public String search(@PageableDefault(size=10) Pageable pageable,
                         @RequestParam("name") String name,
                         @RequestParam("keyword") String keyword, Model model){
        model.addAttribute("historyList", searchService.findAllByKeywordWithPageination(name, keyword, pageable));
        model.addAttribute("userName", name);
        model.addAttribute("keyword", keyword);
        return "log";
    }

    @GetMapping("/log-detail")
    public String keywordLog(@PageableDefault(size=10)Pageable pageable,
                             @RequestParam("name") String name,
                             @RequestParam("keyword") String keyword,
                             @RequestParam("type") SortType type, Model model) {
        model.addAttribute("urlList", searchService.findOneHistory(name, keyword, type, pageable));
        model.addAttribute("userName", name);
        model.addAttribute("keyword", keyword);
        return "log-detail";
    }
}
