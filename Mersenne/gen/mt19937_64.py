# several 'magic' constants are taken as given here
# investigating their origins is beyond the scope of this kind of project
# note that in Python 3: int/int = float

class mt19937_64(object): # MT19937 64-bit variant of the Mersenne Twister prng
    def __init__(self,seed): # initializes the generator with a seed
        self.idx = 0
        self.mt = [seed]*312
        for i in range(1,312):
            self.mt[i] = 0xffffffffffffffff & (0x5851f42d4c957f2d * (self.mt[i-1] ^ self.mt[i-1]>>62) + i)
    
    def generate(self): # generates 312 new numbers for extraction method
        for i in range(0,312):
            y = (self.mt[i] & 0xffffffff80000000) + (self.mt[(i+1)%312] & 0x7fffffff)
            self.mt[i] = self.mt[(i+156)%312] ^ y>>1
            if (y%2 != 0):
                self.mt[i] = self.mt[i] ^ 0xb5026f5aa96619e9
    
    def extract(self): # extracts an integer from [0, 2**64-1]
        if (self.idx == 0):
            self.generate()
        y = self.mt[self.idx]
        y ^= y>>29 & 0x5555555555555555
        y ^= y<<17 & 0x71d67fffeda60000
        y ^= y<<37 & 0xfff7eee000000000
        y ^= y>>43
        ++self.idx
        self.idx %= 312
        return y
    
    def vectorize(self,n): # extracts n floats from [0,1]
        v = [0]*n
        for i in range(0,n):
            v[i] = self.extract()/0xffffffffffffffff
        return v
