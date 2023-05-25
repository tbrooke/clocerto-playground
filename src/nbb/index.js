const { Template, Clause } = require("@accordproject/cicero-core");
const { Engine } = require("@accordproject/cicero-engine");

async function loadTemplate() {
  const template = await Template.fromUrl(
    "https://templates.accordproject.org/archives/helloworldstate@0.15.0.cta"
  );
  console.log(`Loaded: ${template.getName()}`);
  const templateModelClass = template.getTemplateModel();
  console.log(`Type for the template: ${templateModelClass.getFullyQualifiedName()}`);

  // const mm = template.getLogicManager().getModelManager();
  // mm.getModels().forEach((model) => {
  //   console.log(`==== ${model.name} ====`);
  //   console.log(model.content);
  //   console.log('=======================');
  // });

  const state = {
    "$class": "org.accordproject.helloworldstate.HelloWorldState",
    "counter": 42,
    "$identifier": "7f1c01d0-a77b-11eb-9770-7ddd576a12c2"
  };

  const request = {
    "$class": "org.accordproject.helloworldstate.MyRequest",
    "input": "World",
    "$timestamp": "2021-04-27T13:10:42.028-04:00"
  };

  const clause = new Clause(template);
  clause.setData({
    "$class": "org.accordproject.helloworldstate.HelloWorldClause",
    "name": "Fred Blogs",
    "clauseId": "c1a6cbf4-c678-4e2c-9247-e8da60fb1eb5",
    "$identifier": "c1a6cbf4-c678-4e2c-9247-e8da60fb1eb5"
  });
  const engine = new Engine();
  const result = await engine.trigger(clause, request, state);
  console.log(JSON.stringify(result, null, 2));
}

loadTemplate();