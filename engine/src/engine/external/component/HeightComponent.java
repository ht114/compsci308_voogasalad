package engine.external.component;

public class HeightComponent extends Component<Double> {

    private final static double DEFAULT = 10.0;

    public HeightComponent() {
        super(DEFAULT);
    }

    public HeightComponent(Double value) {
        super(value);
    }


}
