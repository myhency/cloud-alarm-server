package stock.alarm.cloud.analyze;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


public class AnalyzedItemResource extends EntityModel<AnalyzedItem> {

    public AnalyzedItemResource(AnalyzedItem analyzedItem, Link... links) {
        super(analyzedItem, links);
        add(linkTo(AnalyzeController.class).slash(analyzedItem.getId()).withSelfRel());
    }
}
