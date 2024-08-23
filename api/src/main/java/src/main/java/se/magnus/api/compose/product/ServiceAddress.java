package src.main.java.se.magnus.api.compose.product;

public class ServiceAddress {

    private final String cmp;
    private final String pro;
    private final String rev;
    private final String rec;

    public ServiceAddress(){
        cmp = null;
        pro = null;
        rev = null;
        rec = null;
    }

    public ServiceAddress(
            String compositeAddress,
            String productAddress,
            String reviewAddress,
            String recommendationAddress) {
        this.cmp = compositeAddress;
        this.pro = productAddress;
        this.rev = reviewAddress;
        this.rec = recommendationAddress;
    }

    public String getCmp() {
        return cmp;
    }

    public String getRec() {
        return rec;
    }

    public String getRev() {
        return rev;
    }

    public String getPro() {
        return pro;
    }
}
