.PHONY: all clean

all: morescala.pdf

morescala.pdf: morescala.tex ../common/*.tex
	LANG="en" git log -1 --format=%cd --date='format:%B %d, %Y' $< > date.tex
	latexmk -pdf -pdflatex="pdflatex -interactive=nonstopmode" -use-make $<

clean:
	latexmk -CA
