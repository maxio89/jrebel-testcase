package pl.itcrowd.base.country.view;

import org.jboss.seam.international.status.Messages;
import pl.itcrowd.base.Constants;
import pl.itcrowd.base.country.business.CountryHome;
import pl.itcrowd.base.country.business.CountryRegionHome;
import pl.itcrowd.base.domain.Country;
import pl.itcrowd.base.domain.CountryRegion;
import pl.itcrowd.base.user.business.UserHome;
import pl.itcrowd.base.web.BundleKeys;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ConversationScoped
public class CountryListView implements Serializable {

    private List<Country> allCountries = new ArrayList<Country>();

    private Conversation conversation;

    private CountryHome countryHome;

    private CountryRegionHome countryRegionHome;

    private Messages messages;

    private Country selectedCountry;

    private CountryRegion selectedCountryRegion;

    private UserHome userHome;

    @SuppressWarnings({"UnusedDeclaration", "CdiInjectionPointsInspection"})
    @Inject
    public CountryListView(CountryHome countryHome, CountryRegionHome countryRegionHome, UserHome userHome, Conversation conversation, Messages messages)
    {
        this.countryHome = countryHome;
        this.countryRegionHome = countryRegionHome;
        this.userHome = userHome;
        this.conversation = conversation;
        this.messages = messages;
    }

    @SuppressWarnings("UnusedDeclaration")
    public CountryListView()
    {
    }

    public List<Country> getAllCountries()
    {
        return allCountries;
    }

    public Country getEditedCountryHome()
    {
        return countryHome.getInstance();
    }

    public CountryRegion getEditedRegionHome()
    {
        return countryRegionHome.getInstance();
    }

    public Country getSelectedCountry()
    {
        return selectedCountry;
    }

    public void setSelectedCountry(Country selectedCountry)
    {
        this.selectedCountry = selectedCountry;
        countryHome.clearInstance();
        if (selectedCountry == null || selectedCountry.getId() == null) {
            countryHome.setInstance(selectedCountry);
        } else {
            countryHome.setId(selectedCountry.getId());
        }
    }

    public CountryRegion getSelectedCountryRegion()
    {
        return selectedCountryRegion;
    }

    public void setSelectedCountryRegion(CountryRegion selectedCountryRegion)
    {
        this.selectedCountryRegion = selectedCountryRegion;
        countryRegionHome.clearInstance();
        if (selectedCountryRegion == null || selectedCountryRegion.getId() == null) {
            countryRegionHome.setInstance(selectedCountryRegion);
        } else {
            countryRegionHome.setId(selectedCountryRegion.getId());
        }
    }

    public String deleteCountry()
    {
        countryHome.clearInstance();
        countryHome.setId(selectedCountry.getId());
        countryHome.remove();
        initView();
        newCountry();
        messages.info(BundleKeys.COUNTRY_DELETE);
        return Constants.OUTCOME_SUCCESS;
    }

    public String deleteRegion()
    {
        countryRegionHome.clearInstance();
        countryRegionHome.setId(selectedCountryRegion.getId());
        countryHome.getInstance().getRegions().remove(countryRegionHome.getInstance());
        countryHome.update();
        initView();
        newRegion();
        return Constants.OUTCOME_SUCCESS;
    }

    public void initConversation()
    {
        if (conversation.isTransient()) {
            conversation.begin();
        }
    }

    public void initView()
    {
        initConversation();
        allCountries = countryHome.getAllCountries();
    }

    public boolean isSelectedRegion()
    {
        return selectedCountryRegion != null;
    }

    public boolean isUnselectedRegion()
    {
        return selectedCountryRegion == null;
    }

    public String newCountry()
    {
        selectedCountry = new Country();
        setSelectedCountryRegion(null);
        setSelectedCountry(selectedCountry);
        return Constants.OUTCOME_SUCCESS;
    }

    public String newRegion()
    {
        if (selectedCountry == null) {
            return Constants.OUTCOME_FAILURE;
        }
        selectedCountryRegion = new CountryRegion();
        setSelectedCountryRegion(selectedCountryRegion);
        return Constants.OUTCOME_SUCCESS;
    }

    public String save()
    {
        if (!isSelectedRegion()) {
            if (!countryHome.checkDuplicate().isEmpty()) {
                messages.error(BundleKeys.DUPLICATE_COUNTRY);
                return Constants.OUTCOME_FAILURE;
            }
            if (countryHome.isIdDefined()) {
                countryHome.update();
                messages.info(BundleKeys.COUNTRY_UPDATE);
            } else {
                countryHome.getInstance().setRegions(new ArrayList<CountryRegion>());
                countryHome.persist();
                messages.info(BundleKeys.COUNTRY_ADD);
            }
        } else {
            countryRegionHome.getInstance().setCountry(selectedCountry);
            if (countryRegionHome.isIdDefined()) {
                countryRegionHome.update();
            } else {
                countryRegionHome.persist();
                countryHome.getInstance().getRegions().add(selectedCountryRegion);
                countryHome.update();
            }
        }
        initView();
        return Constants.OUTCOME_SUCCESS;
    }

    public void uncheckRegion()
    {
        selectedCountryRegion = null;
    }
}
