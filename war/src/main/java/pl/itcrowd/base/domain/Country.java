package pl.itcrowd.base.domain;


import org.hibernate.validator.constraints.NotEmpty;
import pl.itcrowd.seam3.persistence.Identifiable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "COUNTRY", uniqueConstraints = @UniqueConstraint(name = "UNIQUE___COUNTRY___NAME", columnNames = "NAME"))
public class Country implements Serializable, Identifiable<Long> {

    @Id
    @GeneratedValue(generator = "COUNTRY_ID_SEQUENCE", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "COUNTRY_ID_SEQUENCE", sequenceName = "COUNTRY_ID_SEQUENCE", initialValue = 1, allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @NotEmpty
    @Column(name = "NAME", length = 52, nullable = false)
    private String name;

    @NotNull
    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CountryRegion> regions;

    public Country()
    {
    }

    public Country(String name, List<CountryRegion> regions)
    {
        this.name = name;
        this.regions = regions;
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

    public void setRegions(List<CountryRegion> regions)
    {
        this.regions = regions;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<CountryRegion> getRegions()
    {
        if (regions == null) {
            regions = new ArrayList<CountryRegion>();
        }
        return regions;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Country)) {
            return false;
        }

        Country country = (Country) o;

        return !(this.getId() != null ? !this.getId().equals(country.getId()) : country.getId() != null);
    }

    @Override
    public int hashCode()
    {
        return this.getId() != null ? this.getId().hashCode() : 0;
    }
}
