⍝! apl
⍝ meta: a library for creating and checking meta functions in a library
⍝

⍝ return the meta functions to be provided by Package
⍝
∇Z←meta∆functions Package;MP
 MP←Package,'⍙'   ⍝ the meta prefix
 Z← ,⊂MP,'Author'
 Z←Z,⊂MP,'Version'
 Z←Z,⊂MP,'Portability'
 Z←Z,⊂MP,'License'
 Z←Z,⊂MP,'BugEmail'
 Z←Z,⊂MP,'Requires'
 Z←Z,⊂MP,'Provides'
 Z←Z,⊂MP,'Download'
 Z←Z,⊂MP,'Documentation'
∇

⍝ create all meta functions to be provided by Package
⍝
∇meta∆make_functions Package;Funs
 Funs←meta∆functions Package
 meta∆make_function ¨ Funs
∇

⍝ create one meta function, asking for its value
⍝
∇meta∆make_function Fun;Value;Q
 ⍞←Q←50↑' Value to be returned by ',Fun,':'
 Value←(⍴Q)↓⍞ ◊ ''
 L0←'Z←' , Fun
 L1←'Z←,⊂''', Value, ''''
 Q←⎕FX L0 L1
∇

meta∆make_functions 'meta'

      )FNS
      )OFF

