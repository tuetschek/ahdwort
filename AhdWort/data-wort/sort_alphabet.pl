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
#     * Neither the name of Ondrej Dusek nor the
#       names of his contributors may be used to endorse or promote products
#       derived from this software without specific prior written permission.
#
# THIS SOFTWARE IS PROVIDED BY Ondrej Dusek ''AS IS'' AND ANY
# EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
# WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
# DISCLAIMED. IN NO EVENT SHALL Ondrej Dusek BE LIABLE FOR ANY
# DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
# (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
# LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
# ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
# (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
# SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
# 
#
#         FILE:  sort_alphabet.pl
#
#        USAGE:  ./sort_alphabet.pl wl_in.dat dict_in.dat wl_out.dat dict_out.dat
#
#  DESCRIPTION:  Sorts the dictionary alphabetically.
#            
#                wl_in.dat must contain a list of dictionary entries, on-at a line.
#                       These are the criterion for sorting.
#                
#                dict_in.dat must contain the dictionary entries, one at a line. 
#                       They are sorted in the same order as the entries list.
#
#                wl_out.dat and dict_out.dat are the corresponding output files.
#
#===============================================================================

use strict;
use warnings;
use utf8;


if (@ARGV != 4){
    die("Usage: ./sort_alphabet.pl wl-in dict-in wl-out dict-out\n");
}

my @wordlist = @{read_file($ARGV[0])};
my @dict = @{read_file($ARGV[1])};

print(STDERR scalar(@wordlist) . " " . scalar(@dict) . "\n");

parallel_sort(\@wordlist, \@dict);

write_file(\@wordlist, $ARGV[2]);
write_file(\@dict, $ARGV[3]);


# read file line-by-line
sub read_file {

    my ($file) = @_;
    my @result;

    open(IN, $file) || die("$file not found.\n");
    binmode(IN, ":utf8");

    while (<IN>){
        s/\r?\n$//;
        push(@result, $_);
    }
    close(IN);

    return [@result];
}

# line-based file output
sub write_file {

    my ($data, $file) = @_;

    open(OUT, ">", $file) || die("cannot access $file.\n");
    binmode(OUT, ":utf8");

    foreach my $line (@{$data}){
        print(OUT $line."\n");
    }
    close(OUT);
}

# this does the actual sorting
sub parallel_sort {

    my ($list, $entries) = @_;
    my $swap = 1;
    my $passes = 0;

    if ($#$list != $#$entries){
        die("List and entries do not match in length.\n");
    }
    while($swap){ # cocktail sort
        $swap = 0;
        for (my $i = 0; $i < $#$list - ($passes + 1); ++$i){

            if ($list->[$i] gt $list->[$i+1]){

                my $tmp = $list->[$i];
                $list->[$i] = $list->[$i+1];
                $list->[$i+1] = $tmp;

                $tmp = $entries->[$i]; # sort entries according to list
                $entries->[$i] = $entries->[$i+1];
                $entries->[$i+1] = $tmp;

                $swap++;
            }
        }
        for (my $i = $#$list - ($passes + 2); $i >= 0; --$i){
            
            if ($list->[$i] gt $list->[$i+1]){

                my $tmp = $list->[$i];
                $list->[$i] = $list->[$i+1];
                $list->[$i+1] = $tmp;

                $tmp = $entries->[$i]; # sort entries according to list
                $entries->[$i] = $entries->[$i+1];
                $entries->[$i+1] = $tmp;

                $swap++;
            }
        }
        print(STDERR "$swap ");
    }
}
