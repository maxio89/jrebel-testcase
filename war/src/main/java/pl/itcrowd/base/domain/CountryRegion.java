package pl.itcrowd.base.domain;


import org.hibernate.annotations.ForeignKey;
import org.hibernate.validator.constraints.NotEmpty;
import pl.itcrowd.seam3.persistence.Identifiable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "COUNTRY_REGION")
public class CountryRegion implements Serializable, Identifiable<Long> {

    @NotNull
    @JoinColumn(name = "COUNTRY_ID", nullable = false)
    @ForeignKey(name = "FK___COUNTRY___COUNTRY_REGION")
    @ManyToOne()
    private Country country;

    @Id
    @GeneratedValue(generator = "COUNTRY_REGION_ID_SEQUENCE", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "COUNTRY_REGION_ID_SEQUENCE", sequenceName = "COUNTRY_REGION_ID_SEQUENCE", initialValue = 1, allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @NotEmpty
    @Column(length = 255, name = "NAME", nullable = false)
    private String name;

    public CountryRegion()
    {
    }

    public CountryRegion(String name)
    {
        this.name = name;
    }

    public Country getCountry()
    {
        return country;
    }

    public void setCountry(Country country)
    {
        this.country = country;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CountryRegion)) {
            return false;
        }

        CountryRegion countryRegion = (CountryRegion) o;

        return !(this.getId() != null ? !this.getId().equals(countryRegion.getId()) : countryRegion.getId() != null);
    }

    @Override
    public int hashCode()
    {
        return this.getId() != null ? this.getId().hashCode() : 0;
    }
}
