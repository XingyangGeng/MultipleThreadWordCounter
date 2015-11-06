


abstract class AbstractConcurrencyFactorProvider implements ConcurrencyFactorProvider {

    private final int concurrencyFactor;

    protected AbstractConcurrencyFactorProvider(int concurrencyFactor) {
        this.concurrencyFactor = concurrencyFactor;
    }

    @Override public int getConcurrencyFactor() {
        return concurrencyFactor;
    }

}
