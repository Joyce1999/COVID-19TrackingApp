package com.CA326MyBubble.repositories;

import java.util.ArrayList;
import java.util.List;

import com.CA326MyBubble.model.CountryLocal;

public class CountriesData {
// Taken from NewsAPI.org, a live example of how to create a global news feed for different countries.
   private static final String AFRICA = "Africa";
   private static final String ASIA = "Asia";
// Africian country names
    private String[] countriesAfrica = {"Algeria", "Angola", "Benin", "Botswana", "Burkina Faso", "Burundi", "Cabo Verde", "Cameroon", "Central African Republic", "Chad",
            "Comoros", "Congo", "Cote d'Ivoire", "Djibouti", "DRC", "Egypt", "Equatorial Guinea", "Eritrea", "Eswatani", "Ethiopia",
            "Gabon", "Gambia", "Ghana", "Guinea", "Guinea-Bissau", "Kenya", "Lesotho", "Liberia", "Libya", "Madagascar",
            "Malawi", "Mali", "Mauritania", "Mauritius", "Morocco", "Mozambique", "Namibia", "Niger", "Nigeria", "Rwanda",
            "Soa Tome & Principe", "Senegal", "Seychelles", "Sierra Leone", "Somalia", "South Africa", "South Sudan", "Sudan", "Tanzania", "Togo",
            "Tunisia", "Uganda", "Zambia", "Zimbabwe"};
// If a country has a code we can get the national news for that country
    private String[] codeAfrica =  {"","","", "", "", "", "", "", "", "",
            "", "", "", "", "", "eg", "", "", "", "",
            "", "", "", "", "", "", "", "", "", "",
            "", "", "", "ma", "", "", "", "", "ng", "",
            "", "", "", "", "", "za", "", "", "", "",
            "", "", "", ""};


    //Europe
    private String[] countriesEurope = {"Albania", "Andorra", "Austria", "Belarus", "Belgium", "Bosnia", "Bulgaria", "Croatia", "Czechia", "Denmark",
            "Estonia", "Finland", "France", "Germany", "Greece", "Hole See", "Hungary", "Iceland", "Italy", "Ireland", "Latvia",
            "Liechtenstein", "Lithuania", "Luxembourg", "Malta", "Moldova", "Monaco", "Montenegro", "Netherlands", "North Macedonia", "Norway",
            "Poland", "Portugal", "Romania", "Russia", "San Marino", "Serbia", "Slovakia", "Slovenia", "Spain", "Sweden",
            "Switzerland", "Ukraine", "United Kingdom" };
    // If a country has a code we can get the national news for that country
    private String[] codeEurope = {"", "", "", "", "be", "", "bg", "", "cz", "",
            "", "", "fr", "ge", "gr", "", "hu", "ie", "it", "ie", "",
            "", "", "", "", "", "", "", "nl", "", "",
            "pl", "pt", "ro", "ru", "", "rs", "", "si", "", "se",
            "ch", "", "gb"};

    //Asia
    private String[] countriesAsia = {"Afghanistan","Armenia", "Azerbaijan", "Bahrain", "Bangladesh", "Bhutan", "Brunei", "Cambodia", "China", "Cyprus",
            "Georgia","Hong Kong", "India", "Indonesia", "Iran", "Iraq", "Israel", "Japan", "Jordan", "Kazakhstan",
            "Laos", "Lebanon", "Malaysia", "Maldives", "Mongolia", "Myanmar", "Nepal", "North Korea", "Oman", "Pakistan",
            "Philippines", "Qatar", "Saudi Arabia", "Singapore", "South Korea", "Sri Lanka", "Palestine", "Syria", "Tajikistan", "Taiwan",
            "Thailand", "Timor-Leste", "Turkey", "Turkmenistan", "UAE" };
    // If a country has a code we can get the national news for that country
    private String[] codeAsia = {"","", "", "", "", "", "", "", "cn", "",
            "","hk", "in", "", "", "", "il", "", "", "",
            "", "", "my", "", "", "", "", "", "", "",
            "ph", "", "sa", "sq", "kr", "", "", "", "", "tw",
            "th", "", "tr", "", "ae" };

    //North America
    private String[]countriesNorthAmerica = {"Antigua and Barbuda", "Bahamas", "Barbados", "Canada", "Costa Rica", "Cuba", "Dominica", "Dominican Republic", "El Salvador", "Grenada",
            "Guatemala", "Haiti", "Honduras", "Jamaica", "Mexico", "Nicaragua", "Panama", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and Grenadines",
            "Trinidad and Tobago", "USA"};
    // If a country has a code we can get the national news for that country
    private String[]codesNorthAmerica = {"", "", "", "ca", "", "cu", "", "", "", "",
            "", "", "", "", "mx", "", "", "", "", "",
            "", "us"};


    //South America
    private String[]countriesSouthAmerica = {"Argentina", "Bolivia", "Brazil", "Chile", "Colombia", "Ecuador", "Guyana", "Paraguay", "Peru", "Suriname", "Uruguay", "Venezuela"};
    // If a country has a code we can get the national news for that country
    private String[]codesSouthAmerica   = {"ar", "", "", "", "co", "", "", "", "", "", "", "ve"};


    //Australia/Oceania
    private String[]countriesAutraliaOceanea= {"Australia", "Fiji", "Kiribati", "Marshall Islands", "Micronesia", "Nauru", "New Zealand", "Palau", "Papua New Guinea", "Samoa",
            "Solomon Islands", "Tonga", "Tuvalu", "Vanuatu"};
    // If a country has a code we can get the national news for that country
    private String[]codesAutraliaOceanea= {"au", "", "", "", "", "", "nz", "", "", "",
            "", "", "", ""};


    // Creates a list of the countries
    private List<CountryLocal> countryLocalList = new ArrayList<>();

    public List<CountryLocal> getCountryLocalList() {

        for (int i= 0; i < countriesAfrica.length; i++)
        {
            CountryLocal countryLocal = new CountryLocal();
            countryLocal.setName(countriesAfrica[i]);
            countryLocal.setContinent("Africa");
            countryLocal.setCode(codeAfrica[i]);
            countryLocalList.add(countryLocal);
        }
        for (int i= 0; i < countriesEurope.length; i++)
        {
            CountryLocal countryLocal = new CountryLocal();
            countryLocal.setName(countriesEurope[i]);
            countryLocal.setContinent("Europe");
            countryLocal.setCode(codeEurope[i]);
            countryLocalList.add(countryLocal);
        }
        for (int i= 0; i < countriesNorthAmerica.length; i++)
        {
            CountryLocal countryLocal = new CountryLocal();
            countryLocal.setName(countriesNorthAmerica[i]);
            countryLocal.setContinent("North America");
            countryLocal.setCode(codesNorthAmerica[i]);
            countryLocalList.add(countryLocal);
        }
        for (int i= 0; i < countriesAsia.length; i++)
        {
            CountryLocal countryLocal = new CountryLocal();
            countryLocal.setName(countriesAsia[i]);
            countryLocal.setContinent("Asia");
            countryLocal.setCode(codeAsia[i]);
            countryLocalList.add(countryLocal);
        }
        for (int i= 0; i < countriesSouthAmerica.length; i++)
        {
            CountryLocal countryLocal = new CountryLocal();
            countryLocal.setName(countriesSouthAmerica[i]);
            countryLocal.setContinent("South America");
            countryLocal.setCode(codesSouthAmerica[i]);
            countryLocalList.add(countryLocal);
        }
        for (int i= 0; i < countriesAutraliaOceanea.length; i++)
        {
            CountryLocal countryLocal = new CountryLocal();
            countryLocal.setName(countriesAutraliaOceanea[i]);
            countryLocal.setContinent("Australia/Oceania");
            countryLocal.setCode(codesAutraliaOceanea[i]);
            countryLocalList.add(countryLocal);
        }

        return countryLocalList;
    }

    public CountryLocal getCountryDetails(String name)
    {
        List<CountryLocal> countries = getCountryLocalList();
        CountryLocal countryLocal = new CountryLocal();
        for (int i= 0; i<countries.size();i++)
        {
            if (name.equals(countries.get(i).getName()));
            {
                countryLocal = countries.get(i);
            }
        }
        return countryLocal;
    }
}
