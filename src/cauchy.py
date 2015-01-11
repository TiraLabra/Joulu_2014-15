import gen
import dist
import stats
 
def main():
    n = 6240
    s = 0x345678
    g = gen.mt19937(s)
    d = dist.cauchy(g.randarray(n),0,1)
    stats.info(d)

if __name__ == '__main__':
    main()
