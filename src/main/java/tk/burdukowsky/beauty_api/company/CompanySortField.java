package tk.burdukowsky.beauty_api.company;

public enum CompanySortField {
    id("id"),
    companyType("company_type"),
    name("name"),
    rating("rating");

    private String strVal;

    private CompanySortField(String strVal) {
        this.strVal = strVal;
    }

    @Override
    public String toString() {
        return strVal;
    }
}
