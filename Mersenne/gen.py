class mt19937(object): # MT19937 variant of the Mersenne Twister prng
    def __init__(self,seed):
        self.idx = 0
        self.mt = [seed]*624
        for i in range(1,624):
            self.mt[i] = 0xffffffff & (0x6c078965 * (self.mt[i-1]^(self.mt[i-1]>>30)) + i)
    
    def generate(self):
        for i in range(0,624):
            y = (self.mt[i] & 0x80000000) + (self.mt[(i+1)%624] & 0x7fffffff)
            self.mt[i] = self.mt[(i+397)%624] ^ (y>>1)
            if (y%2 != 0):
                self.mt[i] = self.mt[i] ^ 0x9908b0df
    
    def extract(self):
        if (self.idx == 0):
            self.generate()
        y = self.mt[self.idx]
        y ^= (y>>11)
        y ^= ((y<<7) & 0x9d2c5680)
        y ^= ((y<<15) & 0xefc60000)
        y ^= (y>>18)
        ++self.idx
        self.idx %= 624
        return y
    
    def vectorize(self,n):
        v = [0]*n
        for i in range(0,n):
            v[i] = self.extract()/0xffffffff
        return v
