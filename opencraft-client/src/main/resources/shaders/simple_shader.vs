#version 330

in vec3 pos;
in vec4 col;
in vec2 tex_coord;

uniform mat4 model;
uniform mat4 cam;

out vec4 Col;
out vec2 Tex_coord;

void main()
{
    gl_Position = cam * model * vec4(pos, 1.0f);

    Col = col;
    Tex_coord = tex_coord;
}