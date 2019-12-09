package com.jindanupajit.jedi.plugins.jedidemo.jedi;


import com.jindanupajit.jedi.plugins.jedidemo.jedi.Jedi;
import org.springframework.data.repository.CrudRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;


public interface SCrudController<REPOSITORY extends CrudRepository, ENTITY> {

    REPOSITORY getRepository();

    ENTITY newEntity();

    @ModelAttribute
    default void init() {

    }

    @GetMapping("/search")
    default String search(Model model) {
        return "auto/entrypoint";
    }

    @GetMapping("/create")
    default String create(Model model) {
        JediModelAttributes modelAttributes = new JediModelAttributes();
        modelAttributes.set(200, Jedi.readFrom(newEntity()));
        return modelAttributes.view(model);
    }

    @GetMapping("/retrieve")
    default String retrieve(Model model, @RequestParam long id) {

        @SuppressWarnings("unchecked")
        Optional<ENTITY> data = getRepository().findById(id);

        if (!data.isPresent()) {
            model.addAttribute("jediResponse", 404);
            model.addAttribute("jediEntity", Jedi.readFrom(newEntity()));
        } else {
            model.addAttribute("jediResponse", 200);
            model.addAttribute("jediEntity", Jedi.readFrom(data.get()));
        }
        return "auto/entrypoint";
    }

    @GetMapping("/update")
    default String update(Model model, @ModelAttribute ENTITY jediEntityForm) {

        Jedi<ENTITY> jedi = Jedi.asJedi(jediEntityForm);


        Object id = jedi.getId();
        Optional data;
        if (id != null) {

            data = getRepository().findById(id);
        }else {
            model.addAttribute("jediResponse", 404);
            model.addAttribute("jediEntity", Jedi.readFrom(newEntity()));
            return "auto/entrypoint";
        }

        if (!data.isPresent()) {
            model.addAttribute("jediResponse", 404);
            model.addAttribute("jediEntity", Jedi.readFrom(newEntity()));
            return "auto/entrypoint";
        } else {
            model.addAttribute("jediResponse", 200);
            model.addAttribute("jediEntity", Jedi.readFrom(data.get()));
        }
        return "auto/entrypoint";
    }

    @GetMapping("/delete")
    default String delete() {
        return "auto/entrypoint";
    }
}
