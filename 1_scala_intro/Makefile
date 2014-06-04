.PHONY: all clean

all: scala.pdf

scala.pdf: scala.tex ../common/*.tex
	latexmk -pdf -pdflatex="pdflatex -interactive=nonstopmode" -use-make scala.tex

clean:
	latexmk -CA
