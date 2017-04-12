0
import scala.math

def height(x:Double, y:Double):Double =
  math.sin(10*math.Pi*x) * math.sin(10*math.Pi*y) - 10*(math.pow(x-0.5, 2) + math.pow(y-0.5, 2))
