package pl.itcrowd.base.framework.view;

import org.jboss.seam.international.status.Messages;
import org.jboss.solder.core.Veto;
import org.jboss.solder.logging.Logger;
import pl.itcrowd.base.domain.Language;
import pl.itcrowd.base.domain.Translatable;
import pl.itcrowd.base.framework.business.EntityHome;
import pl.itcrowd.base.framework.business.EntitySelected;
import pl.itcrowd.base.language.LanguageList;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"CdiManagedBeanInconsistencyInspection", "UnusedDeclaration"})
@Veto
public abstract class AbstractTranslatableDetailsView<E extends Translatable<T>, T> extends AbstractDetailsView<E> {
// ------------------------------ FIELDS ------------------------------

    protected LanguageList languageList;

    protected List<Language> languages;

// --------------------------- CONSTRUCTORS ---------------------------

    /**
     * Required for WELD-001435: Normal scoped bean needs no-args constructor to be proxiable
     */
    protected AbstractTranslatableDetailsView()
    {
        super();
    }

    protected AbstractTranslatableDetailsView(@Nonnull Logger logger, @Nonnull Messages messages, @Nonnull LanguageList languageList)
    {
        super(logger, messages);
        this.languageList = languageList;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public List<Language> getLanguages()
    {
        return languages;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override
    @Nonnull
    public String createNew()
    {
        final String result = super.createNew();
        prepareTranslations(getHome().getInstance());
        return result;
    }

    @PostConstruct
    public void init()
    {
        languages = languageList.getResultList();
        createNew();
    }

    @Override
    @Nonnull
    public String save()
    {
        final EntityHome<E> home = getHome();
        final E instance = home.getInstance();
        for (Iterator<T> iterator = instance.getTranslations().values().iterator(); iterator.hasNext(); ) {
            if (!isValid(iterator.next())) {
                iterator.remove();
            }
        }
        final String result = super.save();
        /**
         * Caution! home.getInstance() may return different element then instance due to setInstance(eM.merge(instance)), and merge may return new instance.
         */
        prepareTranslations(home.getInstance());
        return result;
    }

    @Override
    public void select(@Observes(notifyObserver = Reception.IF_EXISTS) @EntitySelected E entity)
    {
        super.select(entity);
        prepareTranslations(getHome().getInstance());
    }

    protected abstract T createTranslation(Language language, E entity);

    protected abstract boolean isValid(T translation);

    private void prepareTranslations(E entity)
    {
        final Map<Language, T> translations = entity.getTranslations();
        final Set<Long> languageIds = new HashSet<Long>();
        for (Language language : translations.keySet()) {
            languageIds.add(language.getId());
        }
        for (Language language : languages) {
            if (!languageIds.contains(language.getId())) {
                translations.put(language, createTranslation(language, entity));
            }
        }
    }
}
