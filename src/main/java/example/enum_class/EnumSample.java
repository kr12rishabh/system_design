package example.enum_class;

public enum EnumSample {
    MONDAY {
        @Override
        public void abstractMethods() {
            System.out.println("Monday abstract method...");
        }
    },
    TUESDAY {
        @Override
        public void abstractMethods() {

            System.out.println("Monday abstract method...");

        }
    },
    WEDNESDAY {
        @Override
        public void abstractMethods() {

            System.out.println("Monday abstract method...");

        }
    };

    public abstract void abstractMethods();
}
