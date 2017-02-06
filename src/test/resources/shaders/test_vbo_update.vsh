#version 330

// must be inside each shader
in vec3 position;

uniform mat4 camera;

void main()
{
   gl_Position = camera * vec4(position, 1f);
}