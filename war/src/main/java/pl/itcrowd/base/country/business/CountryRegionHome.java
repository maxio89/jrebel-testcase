package pl.itcrowd.base.country.business;



import pl.itcrowd.base.domain.CountryRegion;
import pl.itcrowd.base.framework.business.EntityHome;
import pl.itcrowd.seam3.persistence.EntityRemoved;

import javax.enterprise.util.AnnotationLiteral;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CountryRegionHome extends EntityHome<CountryRegion> {

    @Override
    public int remove(Collection<CountryRegion> elements)
    {
        if (elements.isEmpty()) {
            return 0;
        }
        final Set<Long> ids = new HashSet<Long>();
        for (CountryRegion message : elements) {
            ids.add(message.getId());
        }
        ids.remove(null);

        final int count = getEntityManager().createQuery("delete CountryRegion where id in (:ids)").setParameter("ids", ids).executeUpdate();
        for (CountryRegion element : elements) {
            beanManager.fireEvent(element, new AnnotationLiteral<EntityRemoved>() {
            });
        }
        return count;
    }
}
