.PHONY: all clean

all: scala.pdf

scala.pdf: scala.tex ../common/*.tex
	LANG="en" git log -1 --format=%cd --date='format:%B %d, %Y' $< > date.tex
	latexmk -pdf -pdflatex="lualatex -interactive=nonstopmode" -use-make $<

clean:
	latexmk -CA
