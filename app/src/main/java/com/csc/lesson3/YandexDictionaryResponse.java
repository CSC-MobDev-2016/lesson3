package com.csc.lesson3;

/**
 * Created by roman on 12.03.2016.
 */

public final class YandexDictionaryResponse {
    public final Head head;
    public final Def def[];

    public YandexDictionaryResponse(Head head, Def[] def){
        this.head = head;
        this.def = def;
    }

    public static final class Head {
        public Head() {}
    }

    public static final class Def {
        public final String text;
        public final String pos;
        public final String gen;
        public final String anm;
        public final Tr tr[];

        public Def(String text, String pos, String gen, String anm, Tr[] tr){
            this.text = text;
            this.pos = pos;
            this.gen = gen;
            this.anm = anm;
            this.tr = tr;
        }

        public static final class Tr {
            public final String text;
            public final String pos;
            public final Syn syn[];
            public final Mean mean[];
            public final Ex ex[];

            public Tr(String text, String pos, Syn[] syn, Mean[] mean, Ex[] ex){
                this.text = text;
                this.pos = pos;
                this.syn = syn;
                this.mean = mean;
                this.ex = ex;
            }

            public static final class Syn {
                public final String text;
                public final String pos;

                public Syn(String text, String pos){
                    this.text = text;
                    this.pos = pos;
                }
            }

            public static final class Mean {
                public final String text;

                public Mean(String text){
                    this.text = text;
                }
            }

            public static final class Ex {
                public final String text;
                public final Tr tr[];

                public Ex(String text, Tr[] tr){
                    this.text = text;
                    this.tr = tr;
                }
            }
        }
    }
}
