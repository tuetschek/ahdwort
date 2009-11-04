#!/usr/bin/perl 
#===============================================================================
# Copyright (c) 2009, Ondrej Dusek
# All rights reserved.
#
# Redistribution and use in source and binary forms, with or without
# modification, are permitted provided that the following conditions are met:
#     * Redistributions of source code must retain the above copyright
#       notice, this list of conditions and the following disclaimer.
#     * Redistributions in binary form must reproduce the above copyright
#       notice, this list of conditions and the following disclaimer in the
#       documentation and/or other materials provided with the distribution.
#     * Neither the name of the <organization> nor the
#       names of its contributors may be used to endorse or promote products
#       derived from this software without specific prior written permission.
#
# THIS SOFTWARE IS PROVIDED BY Ondrej Dusek ''AS IS'' AND ANY
# EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
# WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
# DISCLAIMED. IN NO EVENT SHALL <copyright holder> BE LIABLE FOR ANY
# DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
# (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
# LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
# ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
# (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
# SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
# 
#
#         FILE:  build_index.pl
#
#        USAGE:  ./build_index.pl entries.dat words.dat index.dat
#
#  DESCRIPTION:  Creates a word-index to be used with the dictionary.
#            
#                entries.dat must contain dictionary entries captions, one at a 
#                            line, lowercased and alphabetically sorted.
#
#                words.dat   must contain the whole dictionary entries in a simple
#                            JAVA-readable HTML form, enclosed in <p> tags and
#                            one at a line. They must be sorted in the same way as
#                            in the entries.dat file.
#
#                index.dat   is the output file.
#
#===============================================================================

use strict;
use warnings;


if (@ARGV != 3){
    die("USAGE:  ./build_index.pl entries.dat words.dat index.dat\n");
}

my @entries = ();
my @filepos = ();

# reads the names of all the dictionary entries
open(IN, $ARGV[0]) || die("Could not open entries file.\n");
while(<IN>){
    
    s/^\s+//;
    s/\s+$//;

    push(@entries, $_);
}
close(IN);

# finds out the file positions in the dictionary
open(IN, $ARGV[1]) || die("Could not open dictionary file.\n");
push(@filepos, tell(IN));
while(<IN>){
    
    push(@filepos, tell(IN));
}
close(IN);

# writes out the resulting index file
open(OUT, ">", $ARGV[2]) || die("Could not open output.\n");
for (my $i = 0; $i < @entries; ++$i){

    print(OUT "$entries[$i] $filepos[$i]\n");
}
close(OUT);
