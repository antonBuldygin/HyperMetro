import java.util.Optional;

class Phone {

    String ownerName;
    String countryCode;
    String cityCode;
    String number;

    Phone (String ownerName, String number){
        this.ownerName =ownerName;
        this.number =number;}

        Phone (String ownerName, String countryCode, String number, String  cityCode){
            this.ownerName =ownerName;
            this.number =number;
            this.cityCode = cityCode;
            this.countryCode=countryCode;
    }

}