.PHONY: all clean

all: testing.pdf

testing.pdf: testing.tex ../common/*.tex
	latexmk -pdf -pdflatex="pdflatex -interactive=nonstopmode" -use-make testing.tex

clean:
	latexmk -CA
