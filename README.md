[![Unit Tests](https://github.com/antoniusnaumann/pianist-charts/actions/workflows/test.yml/badge.svg)](https://github.com/antoniusnaumann/pianist-charts/actions/workflows/test.yml) [![Example App Desktop/JVM CI](https://github.com/antoniusnaumann/pianist-charts/actions/workflows/desktop-jvm.yml/badge.svg)](https://github.com/antoniusnaumann/pianist-charts/actions/workflows/desktop-jvm.yml) [![Example App Android CI](https://github.com/antoniusnaumann/pianist-charts/actions/workflows/android.yml/badge.svg)](https://github.com/antoniusnaumann/pianist-charts/actions/workflows/android.yml)

# 👷🚧 Under Construction 🛠
This project is currently under development and not supposed to be used at this point

# Pianist Charts
A beautiful chart library for Jetpack Compose (Android) and Compose for Desktop which supports Material 3 themeing.

## About
### Yet another chart library?
Yes! Because unlike most available chart libraries, Pianist aims to focus on styling and themeing to bring beautiful charts to your dashboard apps written in Compose, supporting all the latest and greatest features of Material 3 like Material You dynamic color out of the box.

### Why the name?
For every software tool, there is this mediocre joke game going on with the tools names. For Compose libraries, Google started it with the [Accompanist](https://github.com/google/accompanist) library. So naturally, I had the urge to continue this musical reference game. I found the name Pianist quite fitting, because like a pianist presents a composition to its audience in their own style, the pianist chart library provides charts to present data in a human understandable way in a style compatible with the [Material 3 guidelines](https://m3.material.io).

### Current State

- [ ] Line Chart
  - [x] Draw line from data points (upwards oriented coordinate system)
  - [x] Support Material 3 semantic colors
  - [ ] React to hover / touch events   
    - [ ] Highlight a point on hover / touch (i.e. with vertical line and different point style)
    - [ ] Expose a hover event to display tooltips etc. via a callback
    - [ ] Add a standard tooltip
  - [ ] Add extensive customization options for themeing
    - [x] Change point style
    - [ ] Add support for custom color set
    - [ ] Optionally highlight background under the curve
    - [X] Provide styling for chart background
    - [ ] Options for axis styling and labels
- [ ] Bar Chart
  - *TODO*
- [ ] Multi-Chart (e.g. three line charts and a bar chart overlayed)
  - *TODO*
## License
This library will be available under GPL 3.0 license in the future.

[Contact me](mailto:hi@antonius.dev) if you need to obtain this library under a different license.
