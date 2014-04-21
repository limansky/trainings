.PHONY: all clean

all: futures.pdf

futures.pdf: futures.tex
	latexmk -pdf -pdflatex="pdflatex -interactive=nonstopmode" -use-make futures.tex

clean:
	latexmk -CA
