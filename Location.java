
public class Location
{
    public final int x;
    public final int y;
    
    public Location(int x,int y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public boolean equals(Object o) {
        return (o instanceof Location && ((Location)o).x==this.x && ((Location)o).y==this.y);
    }
    
    @Override
    public int hashCode() {
        return new String(""+x+","+y).hashCode();
    }
    
    @Override
    public String toString() {
        return new String("["+x+","+y+"]");
    }
}
