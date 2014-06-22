⍝! apl --script
⍝
⍝ HTML.apl
⍝
⍝ This file is a GNU APL L1 library containing functions for generating some
⍝ frequently used HTML tags.
⍝
⍝ The library is used by means of the )COPY command:
⍝
⍝ )COPY 5 HTML
⍝
⍝
⍝ The library  provides the following functions (meta functions omitted):
⍝
⍝ HTML∆debug            return debug info to be displayed in a HTML page
⍝ HTML∆Assert           check a condition and complain if not met
⍝ HTML∆xbox		append text matrices A and B horizontally
⍝ HTML∆HTTP_header	CGI header (if started from a web server)
⍝ HTML∆attr		HTML attribute xA with value xB
⍝
⍝ HTML∆_alt		HTML attribute 'alt' with value xB
⍝ HTML∆_content		HTML attribute 'content' with value xB
⍝ HTML∆_height		HTML attribute 'height' with value xB
⍝ HTML∆_href		HTML attribute 'href' with value xB
⍝ HTML∆_http_eq		HTML attribute 'http-equiv' with value xB
⍝ HTML∆_name		HTML attribute 'name' with value xB
⍝ HTML∆__rel		HTML attribute 'rel' with value xB
⍝ HTML∆__src		HTML attribute 'src' with value xB
⍝ HTML∆__type		HTML attribute 'type' with value xB
⍝ HTML∆__width		HTML attribute 'width' with value xB
⍝ HTML∆__h_w		HTML attributes 'height' and 'width' with value xB
⍝
⍝ HTML∆T		HTML tag
⍝ HTML∆TX_B_E		multi-line yB tagged with xA and /xA
⍝ HTML∆TX_B_E_1		single-line xB tagged with xA and /xA
⍝ HTML∆TX_B_1		single-line xB tagged with xA and no /xA
⍝ HTML∆x2y		convert single-line xB to multi-line yZ
⍝ HTML∆indent_2		indent single-line xB
⍝ HTML∆indent_2		indent multi-line xB
⍝ HTML∆emit_1		disclose one-line yB and print it
⍝ HTML∆emit		disclose multi-line yB and print it
⍝ HTML∆A		<A href=xHREF> xB </A>
⍝ HTML∆Title		<TITLE> xB </TITLE>
⍝ HTML∆Img		<IMG xB>
⍝ HTML∆Link		<LINK xB>
⍝ HTML∆Style		<STYLE xX>
⍝ HTML∆Li		<LI> xB
⍝ HTML∆Ol		<OL> yB </OL>
⍝ HTML∆Ul		<UL> yB </UL>
⍝ HTML∆H1		<H1 xX>
⍝ HTML∆H2		<H2 xX>
⍝ HTML∆H3		<H3 xX>
⍝ HTML∆H4		<H4 xX>
⍝ HTML∆Head		<HEAD> yB </HEAD>
⍝ HTML∆Body		<BODY> yB </BODY>
⍝ HTML∆Html		<HTML> yB </HTML>
⍝ HTML∆Document		entire document with CGI header
⍝
⍝ Variable name conventions:
⍝
⍝ Variables starting with x, e.g. xB, are strings (simple vectors of
⍝ characters), i.e. 1≡ ≡xB and 1≡''⍴⍴⍴xB
⍝
⍝ Variables starting with y are vectors of character strings,
⍝ i.e. 2≡ ≡yB and 1≡''⍴⍴⍴yB
⍝
⍝ Certain characters in function names have the following meaning:
⍝
⍝ T - start tag
⍝ E - end tag
⍝ X - attributes
⍝ _ - attribute function


⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝
⍝ pretty-printed (HTML) depth, rank, and shape of B
⍝ A: optional variable name
⍝ B: variable value
⍝
∇Z←A HTML∆debug B;yPROP;yVAL
 yPROP←,⊂'<pre>At ',(,¯2 ⎕SI 3),': ' ◊ V←''
 →1+(0=⎕NC 'A')⍴⎕LC ◊  yPROP←'' ◊ V←A
 yPROP←yPROP,⊂'≡  ',V,': ',,⍕≡B
 yPROP←yPROP,⊂'⍴⍴ ',V,': ',,⍕⍴⍴B
 yPROP←yPROP,⊂'⍴  ',V,': ',,⍕⍴B
 yVAL←⊂[⎕IO+1]4 ⎕CR B
 ⊃yPROP HTML∆xbox yVAL
 '</pre>'
 Z←B
∇

⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝
⍝ check a condition
⍝
∇HTML∆Assert B;COND;LOC;VAR
 →(1≡B)⍴0
 ' '
 COND←7↓,¯2 ⎕SI 4
 LOC←,¯2 ⎕SI 3
 '<pre>************************************************'
 ' '
 '*** Assertion (', COND, ') failed at ',LOC
 ''

 ⍝ show variable (assuming COND ends with the variable name)
 ⍝
 VCHAR←'∆⍙',⎕UCS ,64 96 ∘.+⍳26
 VAR←(⌽∧\⌽(COND∈VCHAR))/COND
 '</pre>'
 0 0⍴VAR HTML∆debug ⍎VAR
 '<pre>'

 ⍝ show stack
 ⍝
 ' '
 'Stack:'
 7 ⎕CR ⊃¯1↓⎕SI 3
 ' '
 '************************************************</pre>'
 →
∇

⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝
⍝ append boxes A and B horizontally:
⍝
⍝ AABBB   AA      BBB
⍝ AABBB ← AA xbox BBB
⍝ AA      AA
⍝
∇yZ←yA HTML∆xbox yB;LenA;H
 HTML∆Assert 2 ≡ ≡yA ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴yA
 HTML∆Assert 2 ≡ ≡yB ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴yB
 LenA←⌈/⍴¨yA
 H←↑(⍴yA)⌈⍴yB
 yA←H↑LenA↑¨yA
 yZ←yA ,¨H↑yB
 HTML∆Assert 2 ≡ ≡yZ ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴yZ
∇

⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝
⍝ generic HTML related helper functions
⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝

⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝
⍝ emit a CGI header (if started from a web server)
∇xZ←HTML∆HTTP_header
 xZ←'' ◊ →(0=⍴,⎕ENV "GATEWAY_INTERFACE")⍴0   ⍝ nothing if not run as CGI script
 ⍝
 ⍝ The 'Content-type: ...' string ends with LF (and no CR)
 ⍝ ⎕UCS 13 10 13 emits CR LF CR.
 ⍝ Together this gives two lines, each ending with CR/LF
 ⍝ Most tolerate LF without CR, but the CGI standard wants CR/LF
 ⍝
 xZ←'Content-type: text/html; charset=UTF-8', ⎕UCS 13 10 13
∇

⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝
⍝ HTML attribute xA with value xB
⍝ xA="xB"
⍝
∇xZ←xA HTML∆attr xB
 HTML∆Assert 1 ≡ ≡xA ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴xA
 HTML∆Assert 1 ≡ ≡xB ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴xB
 xZ←' ',xA,'="',xB,'"'
 HTML∆Assert 1 ≡ ≡xZ ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴xZ
∇

∇xZ←HTML∆_alt  xB
 xZ←"alt" HTML∆attr xB
∇

∇xZ←HTML∆_content xB
 xZ←"content" HTML∆attr xB
∇

∇xZ←HTML∆_height B
 xZ←"height" HTML∆attr ⍕B
∇

∇xZ←HTML∆_href xB
 xZ←"href" HTML∆attr xB
∇

∇xZ←HTML∆_http_eq xB
 xZ←"http-equiv" HTML∆attr xB
∇

∇xZ←HTML∆_name xB
 xZ←"name" HTML∆attr xB
∇

∇xZ←HTML∆__rel  xB
 xZ←"rel" HTML∆attr xB
∇

∇xZ←HTML∆__src  xB
 xZ←"src" HTML∆attr xB
∇

∇xZ←HTML∆__type xB
 xZ←"type" HTML∆attr xB
∇

∇xZ←HTML∆__width B
 xZ←"width" HTML∆attr ⍕B
∇

∇xZ←HTML∆__h_w B
 xZ←(HTML∆_height ↑B), HTML∆__width 1↓B
∇

⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝
⍝ HTML tag xA with attributes xX.
⍝ B determines the tag variant:
⍝
⍝   B = 1: HTML start Tag        <xA xX>
⍝   B = 2: HTML end Tag          </xA>
⍝   B = 3: XML start/end Tag     <xA xX/>
⍝
∇xZ←xA HTML∆T[xX] B
 →1+(0≠⎕NC 'xX')⍴⎕LC ◊ xX←''
 HTML∆Assert 1 ≡ ≡xA ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴xA
 HTML∆Assert 1 ≡ ≡xX ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴xX
 xZ←'<',((B=2)⍴'/'),xA,xX,((B=3)⍴'/'),'>'
 HTML∆Assert 1 ≡ ≡xZ ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴xZ
∇

⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝
⍝ multi-line B tagged with A and /A
⍝ <A X>
⍝   B
⍝ </A>
⍝
∇yZ←xA HTML∆TX_B_E[xX] yB
 →1+(0≠⎕NC 'xX')⍴⎕LC ◊ xX←''
 HTML∆Assert 1 ≡ ≡xA ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴xA
 HTML∆Assert 1 ≡ ≡xX ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴xX
 HTML∆Assert 2 ≡ ≡yB ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴yB
 yZ←,⊂xA HTML∆T[xX] 1
 yZ←yZ,HTML∆indent yB
 yZ←yZ,⊂xA HTML∆T 2
 HTML∆Assert 2 ≡ ≡yZ ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴yZ
∇

⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝
⍝ single-line xB tagged with xA and /xA
⍝ <xA xX> xB </xA>
⍝
∇yZ←xA HTML∆TX_B_E_1[xX] xB
 →1+(0≠⎕NC 'xX')⍴⎕LC ◊ xX←''
 HTML∆Assert 1 ≡ ≡xA ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴xA
 HTML∆Assert 1 ≡ ≡xB ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴xB
 HTML∆Assert 1 ≡ ≡xX ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴xX
 yZ←,⊂(xA HTML∆T[xX] 1),xB,xA HTML∆T 2
 HTML∆Assert 2 ≡ ≡yZ ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴yZ
∇

⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝
⍝ single-line xB tagged with xA (no /A)
⍝ <xA xX> xB
⍝
∇yZ←xA HTML∆TX_B_1[xX] xB
 →1+(0≠⎕NC 'xX')⍴⎕LC ◊ xX←''
 HTML∆Assert 1 ≡ ≡xA ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴xA
 HTML∆Assert 1 ≡ ≡xB ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴xB
 HTML∆Assert 1 ≡ ≡xX ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴xX
 yZ←,⊂(xA HTML∆T[xX] 1),xB
 HTML∆Assert 2 ≡ ≡yZ ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴yZ
∇

⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝
⍝ convert single-line xB to multi-line yZ
⍝
∇yZ←HTML∆x2y xB
 HTML∆Assert 1 ≡ ≡xB ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴xB
 yZ←,⊂xB
 HTML∆Assert 2 ≡ ≡yZ ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴yZ
∇

⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝
⍝ indent single-line xB
⍝
∇xZ←HTML∆indent_2 xB
 xB←,xB
 HTML∆Assert 1 ≡ ≡xB ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴xB
 xZ←'  ',xB
 HTML∆Assert 1 ≡ ≡xZ ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴xZ
∇

⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝
⍝ indent multi-line xB
⍝
∇yZ←HTML∆indent yB
 HTML∆Assert 2 ≡ ≡yB ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴yB
 yZ←HTML∆indent_2 ¨,yB
 HTML∆Assert 2 ≡ ≡yZ ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴yZ
∇

⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝
⍝ disclose one-line yB and print it
⍝
∇HTML∆emit_1 yB
 ⊃yB
∇

⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝
⍝ disclose multi-line yB and print it
⍝
∇HTML∆emit yB
 HTML∆Assert 2 ≡ ≡yB ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴yB
 HTML∆emit_1¨yB
∇

⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝
⍝ specific HTML Elements
⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝

⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝
⍝ tag xB as ANCHOR with URI xHREF
⍝ <A href=xHREF> xB </A>
⍝
∇xZ←xHREF HTML∆A xB
 HTML∆Assert 1 ≡ ≡xB    ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴xB
 HTML∆Assert 1 ≡ ≡xHREF ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴xHREF
 xZ←,⊃"A" HTML∆TX_B_E_1[HTML∆_href xHREF] xB
 HTML∆Assert 1 ≡ ≡xZ ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴xZ
∇

⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝
⍝ tag xB as TITLE
⍝ <TITLE> xB </TITLE>
⍝
∇yZ←HTML∆Title xB
 HTML∆Assert 1 ≡ ≡xB ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴xB
 yZ←'TITLE' HTML∆TX_B_E_1 xB
 HTML∆Assert 2 ≡ ≡yZ ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴yZ
∇

⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝
⍝ tag xB as IMAGE
⍝ <IMG xB>
⍝
∇yZ←HTML∆Img[xX] xB
 →1+(0≠⎕NC 'xX')⍴⎕LC ◊ xX←''
 HTML∆Assert 1 ≡ ≡xX ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴xX
 yZ←,⊂'IMG' HTML∆T[xX] xB
 HTML∆Assert 2 ≡ ≡yZ ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴yZ
∇

⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝
⍝ tag xB as LINK
⍝ <LINK xB>                         ⍝
⍝
∇yZ←HTML∆Link xX
 HTML∆Assert 1 ≡ ≡xX ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴xX
 yZ←,⊂'LINK' HTML∆T[xX] 1
 HTML∆Assert 2 ≡ ≡yZ ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴yZ
∇

⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝
⍝ tag yB as STYLE
⍝ <STYLE xX>
⍝   yB
⍝ </STYLE>
⍝
∇yZ←HTML∆Style[xX] yB
 HTML∆Assert 1 ≡ ≡xX ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴xX
 HTML∆Assert 2 ≡ ≡yB ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴yB
 yZ←'STYLE' HTML∆TX_B_E[xX] yB
 HTML∆Assert 2 ≡ ≡yZ ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴yZ
∇

⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝
⍝ tag xB as LIST ITEM
⍝ <LI> xB
⍝
∇xZ←HTML∆Li xB
 HTML∆Assert 1 ≡ ≡xB ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴xB
 xZ←'<LI> ', xB
 HTML∆Assert 1 ≡ ≡xZ ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴xZ
∇

⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝
⍝ tag yB as ORDERED LIST
⍝ <OL>
⍝   <LI> B[1]
⍝   <LI> B[2]
⍝   ...
⍝ </OL>
⍝
∇yZ←HTML∆Ol[xX] yB
 →1+(0≠⎕NC 'xX')⍴⎕LC ◊ xX←''
 yZ←'OL' HTML∆TX_B_E[xX] HTML∆Li¨yB
∇

⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝
⍝ tag yB as UNORDERED LIST
⍝ <UL>
⍝   <LI> B[1]
⍝   <LI> B[2]
⍝   ...
⍝ </UL>
⍝
∇yZ←HTML∆Ul[xX] yB
 →1+(0≠⎕NC 'xX')⍴⎕LC ◊ xX←''
 yZ←'UL' HTML∆TX_B_E[xX] HTML∆Li¨yB
∇

⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝
⍝ popular HTML elements
⍝
∇yZ←HTML∆H1[xX] xB
 yZ←"H1" HTML∆TX_B_E_1[xX] xB
∇

∇yZ←HTML∆H2[xX] xB
 yZ←"H2" HTML∆TX_B_E_1[xX] xB
∇

∇yZ←HTML∆H3[xX] xB
 yZ←"H3" HTML∆TX_B_E_1[xX] xB
∇

∇yZ←HTML∆H4[xX] xB
 yZ←"H4" HTML∆TX_B_E_1[xX] xB
∇

⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝
⍝ return HEAD using xTITLE
⍝ <HEAD>
⍝   B
⍝ </HEAD>
⍝
∇yZ←HTML∆Head;yB
 yB←HTML∆Title xTITLE
 yB←yB,,⊂'META' HTML∆T[(HTML∆_http_eq 'Content-Type'), HTML∆_content 'text/html; charset=UTF-8'] 1
 yB←yB,,⊂'META' HTML∆T[(HTML∆_name 'description'), HTML∆_content xDESCRIPTION] 1
 yB←yB,HTML∆Link (HTML∆__rel  'stylesheet'),(HTML∆__type 'text/css'), HTML∆_href 'apl-home.css'
 yB←yB,HTML∆Style[HTML∆__type  'text/css'] ,⊂''

 yZ←'HEAD' HTML∆TX_B_E yB
 HTML∆Assert 2 ≡ ≡yZ ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴yZ
∇

⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝
⍝ tag yB as BODY
⍝ <BODY X>
⍝   B
⍝ </BODY>
⍝
∇yZ←HTML∆Body[xX] yB
 HTML∆Assert 2 ≡ ≡yB ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴yB
 yZ←'BODY' HTML∆TX_B_E[xX] yB
 HTML∆Assert 2 ≡ ≡yZ ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴yZ
∇

⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝
⍝ HTML document with body yB
⍝ <HTML X>
⍝   B
⍝ </HTML>
⍝
∇yZ←HTML∆Html[xX] yB
 →1+(0≠⎕NC 'xX')⍴⎕LC ◊ xX←''
 HTML∆Assert 2 ≡ ≡yB ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴yB
 yZ←'HTML' HTML∆TX_B_E HTML∆Head, HTML∆Body[xX] yB
 HTML∆Assert 2 ≡ ≡yZ ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴yZ
∇

⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝
⍝ The entire document
⍝
∇yZ←HTML∆Document
 yZ←    HTML∆x2y HTML∆HTTP_header
 yZ←yZ, HTML∆x2y '<!DOCTYPE html>'
 yZ←yZ, HTML∆Html yBODY
 HTML∆Assert 2 ≡ ≡yZ ◊ HTML∆Assert 1 ≡ ''⍴⍴⍴yZ
∇


  ⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝
 ⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝
⍝⍝                                                                     ⍝⍝
⍝⍝		library meta information...                            ⍝⍝
⍝⍝                                                                     ⍝⍝
 ⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝
  ⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝⍝

∇Z←HTML⍙Author
 Z←,⊂'Jürgen Sauermann'
∇

∇Z←HTML⍙Version
 Z←,⊂'1.0'
∇

∇Z←HTML⍙Portability
 ⍝ this library is L3 because it uses user-defined function with axis, which
 ⍝ is a GNU APL only feature.
 ⍝
 Z←,⊂'L3'
∇

∇Z←HTML⍙License
 Z←,⊂'LGPL (GNU Lesser General Public License)''
∇

∇Z←HTML⍙BugEmail
 Z←,⊂'bug-apl@gnu.org'
∇

∇Z←HTML⍙Requires
 Z←0⍴⊂''
∇

∇Z←HTML⍙Provides
 Z←,⊂'HTML encoding functions'
∇

∇Z←HTML⍙Download;URI;OPTS
 URI←'http://svn.savannah.gnu.org/viewvc/*checkout*/trunk/wslib3/HTML.apl'
 OPTS←'root=apl'
 Z←,⊂URI,'?',OPTS
∇

∇Z←HTML⍙Documentaion
 Z←,⊂'http://www.gnu.org/software/apl/Library-Guidelines.html'
∇

