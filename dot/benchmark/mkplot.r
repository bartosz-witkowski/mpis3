library(ggplot2)
library(plyr)
library(dplyr)
library(reshape2)
library(xtable)

bench.data = read.csv('benchmark.csv', header = TRUE)
bench.data$Benchmark = sub("[^.]*\\.", "", bench.data$Benchmark)

split.bench.data <- split(bench.data, bench.data$rightsCount)

plot_score <- function(dat) {
  rc <- unique(dat$rightsCount)

  ggplot(
    data = dat, 
    mapping = aes(x = Benchmark, y = Score, ymin = Score - Error, ymax = Score + Error)
  ) + 
  geom_point() +
  geom_errorbar() +
  ylab("ops/s") +
  xlab("Benchmark") +
  facet_wrap(facets = vars(arraySize), labeller = "label_both") +
  ggtitle(paste("rightsCount=", rc, sep=""))

  ggsave(
    filename = paste("bench-", rc, ".png", sep=""))
}

lapply(split.bench.data, plot_score)
