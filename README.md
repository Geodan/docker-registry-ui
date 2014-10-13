# Docker Registry Web interface

[![Build Status](https://travis-ci.org/Geodan/docker-registry-ui.svg)](https://travis-ci.org/Geodan/docker-registry-ui)

## Synopsis

This Java application made with Spring Boot, Bootstrap and Handlebars creates a web interface for easy use of the
official Docker Registry. This application has a REST-interface which crawls for available images and tags on 
the filesystem and returns them as a JSON array, which is used in the web interface.

It is recommended that this application is used for internal use only as it has no authentication.

## Installation and quick start

1. Clone this repository 
2. Package it with Maven
3. Run this application!

For optimal use it is recommended that this application is used in combination with our Docker Image or Dockerfile.

## License

```
The MIT License (MIT)

Copyright (c) 2014 Geodan B.V. (Alex van den Hoogen)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```