.PHONY: all clean

all: mongoquery.pdf

mongoquery.pdf: mongoquery.tex ../common/*.tex
	latexmk -pdf -pdflatex="pdflatex -interactive=nonstopmode" -use-make $<

clean:
	latexmk -CA
