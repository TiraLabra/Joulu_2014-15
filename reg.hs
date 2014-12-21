-- Säännöllinen lauseke
data Regex = 
  Null 
  | Empty 
  | Symbol Char 
  | Concat Regex Regex
  | Union Regex Regex
  | Star Regex

-- Näyttäminen merkkijonona
instance Show Regex where
  show Null = "{}"
  show Empty = ""
  show (Symbol c) = c:""
  show (Concat r1 r2) = show r1 ++ show r2
  show (Union r1 r2) = "(" ++ show r1 ++ "|" ++ show r2 ++ ")"
  show (Star r) = let s = show r in case length s of
  	1 -> s ++ "*"
  	_ -> "(" ++ s ++ ")*" 

-- Toteuttaako tyhjä merkkijono annetun regexin?
matchesEmpty :: Regex -> Bool
matchesEmpty Null = False
matchesEmpty Empty = True
matchesEmpty (Symbol _) = False
matchesEmpty (Concat r1 r2) = matchesEmpty r1 && matchesEmpty r2
matchesEmpty (Union r1 r2) = matchesEmpty r1 || matchesEmpty r2
matchesEmpty (Star _) = True

-- Laske regexin derivaatta merkin suhteen.
deriv :: Char -> Regex -> Regex 
deriv c Null = Null
deriv c Empty = Null
deriv c (Symbol d) = if c==d then Empty else Null
deriv c (Concat r1 r2) = let r = Concat (deriv c r1) r2
	in if matchesEmpty r1 then Union r (deriv c r2) else r
deriv c (Union r1 r2)	= Union (deriv c r1) (deriv c r2)
deriv c (Star r) = Concat (deriv c r) (Star r)

-- Laske derivaatan avulla toteuttaako annettu merkkijono annetun regexin.
matchD :: Regex -> String -> Bool
matchD r "" = matchesEmpty r
matchD r (c:s) = matchD (deriv c r) s

-- Parsi regex.
parseRegex :: String -> Maybe Regex  
parseRegex = undefined

