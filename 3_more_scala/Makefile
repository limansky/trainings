.PHONY: all clean

all: morescala.pdf

morescala.pdf: morescala.tex ../common/setup.tex
	latexmk -pdf -pdflatex="pdflatex -interactive=nonstopmode" -use-make morescala.tex

clean:
	latexmk -CA
