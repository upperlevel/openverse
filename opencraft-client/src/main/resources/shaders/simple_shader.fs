#version 450

in vec4 Col;
in vec3 Tex_coord;

uniform vec4 cntChk;

out vec4 res;

uniform sampler2DArray tex;

void main()
{
    res = cntChk * Col * texture(tex, Tex_coord);
}