.PHONY: all clean

all: testing.pdf

testing.pdf: testing.tex ../common/*.tex
	LANG="en" git log -1 --format=%cd --date='format:%B %d, %Y' $< > date.tex
	latexmk -pdf -pdflatex="pdflatex -interactive=nonstopmode" -use-make $<

clean:
	latexmk -CA
