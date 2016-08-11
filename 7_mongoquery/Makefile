.PHONY: all clean

all: mongoquery.pdf

mongoquery.pdf: mongoquery.tex ../common/*.tex
	LANG="en" git log -1 --format=%cd --date='format:%B %d, %Y' $< > date.tex
	latexmk -pdf -pdflatex="pdflatex -interactive=nonstopmode" -use-make $<

clean:
	latexmk -CA
