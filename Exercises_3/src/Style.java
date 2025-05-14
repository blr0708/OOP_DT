public class Style
{
    public final String fillColor;
    public final String strokeColor;
    public final Double strokeWidth;

    public Style(String fillColor, String strokeColor, Double strokeWidth) {
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
        this.strokeWidth = strokeWidth;
    }
    public String toSvg() {
        return String.format("fill:%s; stroke:%s; stroke-width:%.1f;",
                fillColor, strokeColor, strokeWidth);
    }

}
