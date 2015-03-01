.PHONY: all clean

all: reflection.pdf

reflection.pdf: reflection.tex ../common/*.tex
	latexmk -pdf -pdflatex="pdflatex -interactive=nonstopmode" -use-make $<

clean:
	latexmk -CA
