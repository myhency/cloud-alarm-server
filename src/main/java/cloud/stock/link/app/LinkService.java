package cloud.stock.link.app;

import cloud.stock.link.domain.Link;
import cloud.stock.link.infra.LinkRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class LinkService {
    private final LinkRepository linkRepository;

    public String getLink(String linkName) {
        Link link = linkRepository.findByLinkName(linkName).orElseThrow(RuntimeException::new);
        return link.getLinkValue();
    }

    public Long updateLink(Long id, String linkValue) {
        Link link = linkRepository.findById(id).orElseThrow(RuntimeException::new);
        link.setLinkValue(linkValue);
        return linkRepository.save(link).getId();
    }

    public List<Link> getLinks() {
        return linkRepository.findAll();
    }
}
