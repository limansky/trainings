.PHONY: all clean

all: akka.pdf

akka.pdf: akka.tex ../common/*.tex
	latexmk -pdf -pdflatex="pdflatex -interactive=nonstopmode" -use-make akka.tex

clean:
	latexmk -CA
