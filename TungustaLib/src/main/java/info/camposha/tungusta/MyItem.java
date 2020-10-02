package info.camposha.tungusta;

public class MyItem {

    private String id;
    private String shortText1;
    private String category;
    private String longText1;
    private String longText2;
    private String longText3;
    private String shortText2;
    private String shortText3;
    private String shortText4;
    private String shortText5;
    private String shortText6;
    private String shortText7;
    private String shortText8;
    private String imageURL1;

    public static class Builder {

        private String id;
        private String shortText1;
        private String category;
        private String longText1;
        private String longText2;
        private String longText3;
        private String shortText2;
        private String shortText3;
        private String shortText4;
        private String shortText5;
        private String shortText6;
        private String shortText7;
        private String shortText8;
        private String imageURL1;

        public Builder() {
        }

        Builder(String id, String shortText1, String category, String longText1, String longText2, String longText3, String shortText2, String shortText3, String shortText4, String shortText5, String shortText6, String shortText7, String shortText8, String imageURL1) {
            this.id = id;
            this.shortText1 = shortText1;
            this.category = category;
            this.longText1 = longText1;
            this.longText2 = longText2;
            this.longText3 = longText3;
            this.shortText2 = shortText2;
            this.shortText3 = shortText3;
            this.shortText4 = shortText4;
            this.shortText5 = shortText5;
            this.shortText6 = shortText6;
            this.shortText7 = shortText7;
            this.shortText8 = shortText8;
            this.imageURL1 = imageURL1;
        }

        public Builder id(String id){
            this.id = id;
            return Builder.this;
        }

        public Builder shortText1(String shortText1){
            this.shortText1 = shortText1;
            return Builder.this;
        }

        public Builder category(String category){
            this.category = category;
            return Builder.this;
        }

        public Builder longText1(String longText1){
            this.longText1 = longText1;
            return Builder.this;
        }

        public Builder longText2(String longText2){
            this.longText2 = longText2;
            return Builder.this;
        }

        public Builder longText3(String longText3){
            this.longText3 = longText3;
            return Builder.this;
        }

        public Builder shortText2(String shortText2){
            this.shortText2 = shortText2;
            return Builder.this;
        }

        public Builder shortText3(String shortText3){
            this.shortText3 = shortText3;
            return Builder.this;
        }

        public Builder shortText4(String shortText4){
            this.shortText4 = shortText4;
            return Builder.this;
        }

        public Builder shortText5(String shortText5){
            this.shortText5 = shortText5;
            return Builder.this;
        }

        public Builder shortText6(String shortText6){
            this.shortText6 = shortText6;
            return Builder.this;
        }

        public Builder shortText7(String shortText7){
            this.shortText7 = shortText7;
            return Builder.this;
        }

        public Builder shortText8(String shortText8){
            this.shortText8 = shortText8;
            return Builder.this;
        }

        public Builder imageURL1(String imageURL1){
            this.imageURL1 = imageURL1;
            return Builder.this;
        }

        public MyItem build() {
            if(this.id == null){
                throw new NullPointerException("The property \"id\" is null. "
                        + "Please set the value by \"id()\". "
                        + "The properties \"id\", \"shortText1\" are required.");
            }
            if(this.shortText1 == null){
                throw new NullPointerException("The property \"shortText1\" is null. "
                        + "Please set the value by \"shortText1()\". "
                        + "The properties \"id\", \"shortText1\" are required.");
            }

            return new MyItem(this);
        }
    }

    private MyItem(Builder builder) {
        this.id = builder.id;
        this.shortText1 = builder.shortText1;
        this.category = builder.category;
        this.longText1 = builder.longText1;
        this.longText2 = builder.longText2;
        this.longText3 = builder.longText3;
        this.shortText2 = builder.shortText2;
        this.shortText3 = builder.shortText3;
        this.shortText4 = builder.shortText4;
        this.shortText5 = builder.shortText5;
        this.shortText6 = builder.shortText6;
        this.shortText7 = builder.shortText7;
        this.shortText8 = builder.shortText8;
        this.imageURL1 = builder.imageURL1;
    }

    public void doSomething() {
        // do something
    }
}
